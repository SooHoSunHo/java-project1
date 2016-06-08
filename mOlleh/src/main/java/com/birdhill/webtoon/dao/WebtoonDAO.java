package com.birdhill.webtoon.dao;

import com.birdhill.common.dao.AbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("webtoonDAO")
public class WebtoonDAO extends AbstractDAO {


	/*
	 *연동데이터 임시저장 
	 */
	public void insertWebtoonTmp(Map<String, Object> map) throws Exception {
		insert("webtoon.insertWebtoonTmp", map);
	}
	
	/*
	 * tmp -> 실DB저장
	 */
	public void insertWebtoon(Map<String, Object> map) throws Exception {
		insert("webtoon.insertWebtoon", map);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> selectWebtoonTmp() {
		return (List<Map<String,Object>>)selectList("webtoon.selectWebtoonTmp");
	}

	public void deleteWebtoonTmp(Map<String, Object> map) throws Exception {
		delete("webtoon.deleteWebtoonTmp", map);
		
	}
	
	public void deleteWebtoon(Map<String, Object> map) throws Exception {
		delete("webtoon.deleteWebtoon", map);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectWebtoonList(Map<String, Object> map) {
		return (List<Map<String,Object>>) selectList("webtoon.selectWebtoonList", map);
	}

	
}