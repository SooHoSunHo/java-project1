package com.birdhill.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


@Component
public class HttpUtils {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String getHttpClient(String strUrl) throws Exception{
		URI uri = new URI(strUrl);
		
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute((HttpUriRequest) new HttpPost(uri)); //post는 HttpPost사용
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			return content;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getHttpURLConnection(String strUrl) throws Exception {
		BufferedReader rd = null;
		StringBuffer sb = null;
		String line = null;
		
		try {
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			sb = new StringBuffer();
			
			while ((line = rd.readLine()) != null) 
			{
				sb.append(line + '\n');
			}
			rd.close();
			
			return sb.toString();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String saveWebtoonImg(String imagePath, String sWeekCnt) throws Exception{
		
		BufferedImage image = null;
		
		File saveDir = new File("D:/storage/webtoon/"+sWeekCnt);
		if(!saveDir.exists()){
			saveDir.mkdirs();
			logger.debug("D:/storage/webtoon/"+sWeekCnt+ " 디렉터리 생성");
		}
		
		try {
			image = ImageIO.read(new URL(imagePath));
			String fileNm = imagePath.substring(imagePath.lastIndexOf("/") + 1);
			
			File file = new File("D:/storage/webtoon/"+sWeekCnt+"/"+fileNm);
			ImageIO.write(image, "jpg", file);
		
			return fileNm;
		}
		catch(IOException  e){
			e.printStackTrace();
			return null;
		}
	}
}
