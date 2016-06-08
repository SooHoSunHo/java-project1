package com.birdhill.webtoon.service;

import java.util.List;
import java.util.Map;

public interface WebtoonService {
	List<Map<String,Object>> selectWebtoonList(Map<String, Object> map) throws Exception;

	void saveTodayWebtoon(Map<String, Object> map) throws Exception;

	void getHttpClient() throws Exception;
}
