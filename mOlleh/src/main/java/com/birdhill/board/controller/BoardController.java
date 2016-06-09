package com.birdhill.board.controller;

import com.birdhill.board.service.BoardService;
import com.birdhill.common.common.CommandMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Controller
@RequestMapping("/board")
public class BoardController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="boardService")
	private BoardService boardService;
	
	@RequestMapping("/boardList")
	public ModelAndView boardList(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		return mv;
	}
	
	@RequestMapping("/selectBoardList")
	public ModelAndView selectBoardList(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<Map<String,Object>> list = boardService.selectBoardList(commandMap.getMap());
		mv.addObject("list", list);
		if(list.size() > 0){
			mv.addObject("total", list.get(0).get("total_count"));
		}
		else{
			mv.addObject("total", 0);
		}
		
		return mv;
	}
	
	@RequestMapping("/boardWrite")
	public ModelAndView boardWrite(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardWrite");
		
		return mv;
	}
	
	@RequestMapping("/insertBoard")
	public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/board/boardList");
		
		boardService.insertBoard(commandMap.getMap(), request);
		return mv;
	}
	
	@RequestMapping("/boardDetail")
	public ModelAndView boardDetail(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardDetail");

        boardService.updateReadCnt(commandMap.getMap());
		Map<String,Object> map = boardService.selectBoardDetail(commandMap.getMap());

		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	@RequestMapping("/boardUpdate")
	public ModelAndView boardUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardUpdate");
		
		Map<String,Object> map = boardService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	@RequestMapping("/updateBoard")
	public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/board/boardDetail");
		
		boardService.updateBoard(commandMap.getMap(), request);
		
		mv.addObject("idx", commandMap.get("idx"));
		return mv;
	}
	
	@RequestMapping("/deleteBoard")
	public ModelAndView deleteBoard(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/board/boardList");
		
		boardService.deleteBoard(commandMap.getMap());
		
		return mv;
	}
	
	@RequestMapping("/testMapArguementResolver")
	public ModelAndView testMapArguementResolver(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("");
		
		if(commandMap.isEmpty() == false){
			Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
			Entry<String,Object> entry = null;
			while(iterator.hasNext()){
				entry = iterator.next();
				logger.debug("key : " + entry.getKey()+", value : "+entry.getValue());
			}
		}

		return mv;
	}
	
	//test
}
