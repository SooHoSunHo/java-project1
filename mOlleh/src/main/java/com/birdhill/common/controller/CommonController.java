package com.birdhill.common.controller;

import com.birdhill.common.common.CommandMap;
import com.birdhill.common.service.CommonService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController {
	//Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	@RequestMapping("/downloadFile")
	public void downloadFile(CommandMap commandMap, HttpServletResponse response) throws Exception{
		Map<String,Object> map = commonService.selectFileInfo(commandMap.getMap());
		String storedFileName = (String) map.get("stored_file_name");
		String originFileName = (String) map.get("origin_file_name");
		
		byte fileByte[] = FileUtils.readFileToByteArray(new File("D:\\storage\\upload\\"+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(originFileName, "UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
