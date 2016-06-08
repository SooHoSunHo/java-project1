package com.birdhill.common.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CommonDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		return (Map<String,Object>)selectOne("common.selectFileInfo", map);
	}
	
}
