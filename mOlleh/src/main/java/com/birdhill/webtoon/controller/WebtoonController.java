package com.birdhill.webtoon.controller;

import com.birdhill.common.common.CommandMap;
import com.birdhill.webtoon.service.WebtoonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/webtoon")
public class WebtoonController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="webtoonService")
	private WebtoonService webtoonService;
	
	@RequestMapping(value="/webtoonList")
	public ModelAndView webtoonList(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/webtoon/webtoonList");
		
		return mv;
	}
	
	@RequestMapping(value="/selectWebtoonList")
	public ModelAndView selectWebtoonList(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<Map<String,Object>> list = webtoonService.selectWebtoonList(commandMap.getMap());
		mv.addObject("list",list);
		
		return mv;
	}
	
	@RequestMapping(value = "/saveTodayWebtoon")
	public ModelAndView saveTodayWebtoon(CommandMap commandMap) throws Exception{

		ModelAndView mv = new ModelAndView("redirect:/webtoon/webtoonList");
		webtoonService.saveTodayWebtoon(commandMap.getMap());
		
		return mv;
	}
	
	@RequestMapping(value="/getHttpClient")
	public void getHttpClient() throws Exception{
		webtoonService.getHttpClient();
	}
}
