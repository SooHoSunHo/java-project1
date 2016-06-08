package com.birdhill.user.service;

import com.birdhill.user.dao.UserDAO;
import com.birdhill.user.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lg on 2016-05-20.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name="userDAO")
    private UserDAO userDAO;

    @Override
    public void insertUser(UserVO user) throws Exception {
        //this.userDAO.insertUser(user);
        this.userDAO.insert("user.insertUser",user);
    }

    @Override
    public UserVO selectUser(Map<String,Object> map) throws Exception {

        //Map<String,Object> resultMap = new HashMap<String,Object>();
        //Map<String,Object> tempMap = userDAO.selectUser(map);
        //resultMap.put("map", tempMap);

        return userDAO.selectUser(map);
    }

    @Override
    public void updateUser(UserVO user) throws Exception {
        this.userDAO.update("user.updateUser", user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> selectUserList(Map<String, Object> map) throws Exception {
        return (List<Map<String, Object>>) userDAO.selectPagingList("user.selectUserList", map);
    }

    @Override
    public void deleteUser(Map<String, Object> map) throws Exception {
        this.userDAO.delete("user.deleteUser", map);
    }

    @Override
    public boolean isRegistedLoginId(String loginId) throws Exception {

        if ((Integer) this.userDAO.selectOne("user.selectUserCount", loginId) == 0) {
            return false;
        }
        else {
            return true;
        }
    }


}
