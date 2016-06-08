package com.birdhill.webtoon.service;

import com.birdhill.common.util.HttpUtils;
import com.birdhill.webtoon.dao.WebtoonDAO;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("webtoonService")
public class WebtoonServiceImpl implements WebtoonService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String webtoonListUrl = "http://webtoon.olleh.com/api/open/getTodayWorkList.kt?id=molleh&key=50574c774e6c4a654155595247454b687237686e5679324e2f6e46735a4e417562764b6345714d694f5a303d";
	
	@Resource(name="webtoonDAO")
	private WebtoonDAO webtoonDAO;
	
	@Override
	public List<Map<String,Object>> selectWebtoonList(Map<String, Object> map) throws Exception {

		return webtoonDAO.selectWebtoonList(map);
	}

	@Override
	public void saveTodayWebtoon(Map<String, Object> map) throws Exception {

		HttpUtils httpUtils = new HttpUtils();
		//String jsonStr = httpUtils.getHttpURLConnection(webtoonUrl);
		String jsonStr = httpUtils.getHttpClient(webtoonListUrl);
		
		if(jsonStr != null) {
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonStr);
			
			@SuppressWarnings("rawtypes")
			Iterator iterator = jsonArray.iterator();
			
			// tmp db 삭제...
			webtoonDAO.deleteWebtoonTmp(map);
			
			while(iterator.hasNext()){
				JSONObject jObj = (JSONObject) iterator.next();
				
				try {
					Map<String,Object> webtoon = new HashMap<String, Object>();
					
					webtoon.put("authors",jObj.get("authors").toString());
					webtoon.put("webtoonnm",jObj.get("webtoonnm").toString());
					webtoon.put("toonfg", jObj.get("toonfg").toString());
					webtoon.put("thumbpath300",jObj.get("thumbpath300").toString());
					webtoon.put("thumbpath",jObj.get("thumbpath").toString());
					webtoon.put("timesseq",jObj.get("timesseq").toString());
					webtoon.put("weeks", jObj.get("weeks").toString());
					webtoon.put("webtoonseq",jObj.get("webtoonseq").toString());
					webtoon.put("weekcd", map.get("sWeekCnt"));
									
					httpUtils.saveWebtoonImg(jObj.get("thumbpath").toString(), map.get("sWeekCnt").toString());
					
					//상세정보 연동
					//jsonStr = httpUtils.getHttpURLConnection("https://webtoon.olleh.com/api/open/getWork.kt?timesseq="+jObj.get("timesseq")+"&key=50574c774e6c4a654155595247454b687237686e5679324e2f6e46735a4e417562764b6345714d694f5a303d"+"&webtoonseq="+jObj.get("webtoonseq"));
					jsonStr = httpUtils.getHttpClient("https://webtoon.olleh.com/api/open/getWork.kt?timesseq="+jObj.get("timesseq")+"&key=50574c774e6c4a654155595247454b687237686e5679324e2f6e46735a4e417562764b6345714d694f5a303d"+"&webtoonseq="+jObj.get("webtoonseq"));
					logger.debug(jsonStr);
					
					if (jsonStr != null) {
						JSONObject jsonObj = new JSONObject();
						jsonObj = (JSONObject) jsonParser.parse(jsonStr);
						
						webtoon.put("timesviewurl",jsonObj.get("timesviewurl"));
						webtoon.put("timeslisturl",jsonObj.get("timeslisturl"));
						webtoon.put("timestitle",jsonObj.get("timestitle"));
					}
					
					// tmp db insert
					webtoonDAO.insertWebtoonTmp(webtoon);
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			// delete real db
			webtoonDAO.deleteWebtoon(map);
			
			// tmp db to real db
			List<Map<String,Object>> list = webtoonDAO.selectWebtoonTmp();
			for(int i=0;i<list.size();i++) {
				Map<String,Object> webtoon1 = new HashMap<String, Object>();
				String imgpath = list.get(i).get("thumbpath").toString();
				String fileNm = imgpath.substring(imgpath.lastIndexOf("/") + 1);
				
				webtoon1.put("authors",list.get(i).get("authors"));
				webtoon1.put("webtoonnm",list.get(i).get("webtoonnm"));
				webtoon1.put("toonfg",list.get(i).get("toonfg"));
				webtoon1.put("imgpath",fileNm);
				webtoon1.put("imgalt", "썸네일 이미지");
				webtoon1.put("timesseq",list.get(i).get("timesseq"));
				webtoon1.put("timestitle",list.get(i).get("timestitle"));
				webtoon1.put("timesviewurl",list.get(i).get("timesviewurl"));
				webtoon1.put("timeslisturl",list.get(i).get("timeslisturl"));
				webtoon1.put("weekcd", map.get("sWeekCnt"));
				webtoon1.put("webtoonseq", list.get(i).get("webtoonseq"));
				webtoon1.put("dsporder",i);
				// tmp db insert
				webtoonDAO.insertWebtoon(webtoon1);
			}
	
		}
		
	}

	@Override
	public void getHttpClient() throws Exception {
		URI uri = new URI("http://m.olleh.com");
		uri = new URIBuilder(uri).addParameter("aaa", "bbb").addParameter("ccc", "ddd").build();
		
		System.out.println(uri);
	}
	
	//

}
