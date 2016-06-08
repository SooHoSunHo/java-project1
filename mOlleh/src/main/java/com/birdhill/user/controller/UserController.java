package com.birdhill.user.controller;

import com.birdhill.common.common.CommandMap;
import com.birdhill.user.service.UserService;
import com.birdhill.user.vo.Result;
import com.birdhill.user.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("user")
@RequestMapping(value = "/user")
public class UserController {

    @Value("#{systemProperties['os.name']}") String osName;
    @Resource(name="userService")
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    @Autowired
    private Validator validator;


    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        //dataBinder.setDisallowedFields("user_id");
        //dataBinder.setRequiredFields("user_nm", "user_email", "user_mobile", "user_id", "user_age");
        dataBinder.setValidator(validator);
    }
    */


    @ModelAttribute("codes")
    public Map<String,Object> codes(){
        Map<String,Object> map = new Hashtable<String, Object>();

        map.put("myFamily", "최석용,정지연,최수호,최선호");
        map.put("myJob", "programmer,father,husband,step-son");

        return map;
    }

    @RequestMapping("/")
    public String userList(CommandMap commandMap) {
        return "/user/list";
    }

    @RequestMapping("/detail")
    public String userDetail(CommandMap commandMap, Model model) throws Exception {

        model.addAttribute("userInfo", userService.selectUser(commandMap.getMap()));

        return "/user/view";
    }

    @RequestMapping("/selectUserList")
    public ModelAndView selectBoardList(CommandMap commandMap) throws Exception{

        ModelAndView mv = new ModelAndView("jsonView");

        List<Map<String,Object>> list = userService.selectUserList(commandMap.getMap());
        mv.addObject("list", list);
        if(list.size() > 0){
            mv.addObject("total", list.get(0).get("total_count"));
        }
        else{
            mv.addObject("total", 0);
        }

        return mv;
    }

    @RequestMapping(value = "/regist", method = RequestMethod.GET)
    public String userRegForm(@ModelAttribute UserVO user, Model model) {

        //user.setSerialNo(createNewSerialNo());
        user.setUser_pw("1q2w3e4r5t6y");
        model.addAttribute("user", user);

        return "/user/regist";
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String userRegProc(@ModelAttribute UserVO user, BindingResult result, SessionStatus ssStatus, Model model) throws Exception {

        if (result.hasErrors()) {
            logger.debug("============================================" + result.getFieldErrorCount());
            logger.debug("입력에러======================================="+result.toString());
            model.addAttribute("result", result);
            return "/user/regist";
        }
        else {
            this.userService.insertUser(user);
            ssStatus.setComplete();
            return "/user/list";
        }
    }

    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public String userEditForm(CommandMap commandMap, Model model) throws Exception {

        model.addAttribute("user", userService.selectUser(commandMap.getMap()));

        return "/user/edit";
    }

    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public String userEditSubmit(@ModelAttribute @Valid UserVO user, BindingResult bindingResult, SessionStatus ssStatus, Model model, HttpServletRequest request) throws Exception{

        HttpSession session = request.getSession();

        //UserVO info = (UserVO)session.getAttribute("user");
        //user.setUser_id(info.getUser_id());

        if (bindingResult.hasErrors()) {
            for(ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getCode() + " : " + error.getDefaultMessage());
            }

            model.addAttribute("user", user);
            //model.addAttribute("result", result);
            return "/user/edit";
        }
        else {
            this.userService.updateUser(user);
            ssStatus.setComplete();

            return "redirect:/user/";
        }

    }

    @RequestMapping("/delete")
    public String userDelete(CommandMap commandMap) {

        try{
            this.userService.deleteUser(commandMap.getMap());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return "/user/list";
    }

    @RequestMapping(value = "/checkLoginId/{loginId}", method = RequestMethod.GET)
    @ResponseBody
    public Result checkLogin(@PathVariable String loginId) throws Exception {
        Result result = new Result();

        if(userService.isRegistedLoginId(loginId)) {
            result.setDuplicated(true);
            result.setAvailableId(loginId  + (int)(Math. random()*1000));
        }
        else {
            result.setDuplicated(false);
        }

        return result;
    }

}
