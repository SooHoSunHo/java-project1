package com.birdhill.common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class AbstractDAO {
	final static Logger logger = LoggerFactory.getLogger(AbstractDAO.class);
	
	//@Autowired
    @Resource
	private SqlSessionTemplate sqlSession;
	
	protected void printQueryId(String queryId) {
		if(logger.isDebugEnabled()) {
			logger.debug("\t QueryId \t: " + queryId);
		}
	}
	
	public Object insert(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.insert(queryId, params);
    }
     
    public Object update(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.update(queryId, params);
    }
     
    public Object delete(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.delete(queryId, params);
    }
     
    public Object selectOne(String queryId){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId);
    }
     
    public Object selectOne(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId, params);
    }
     
    @SuppressWarnings("rawtypes")
	public List selectList(String queryId){
        printQueryId(queryId);
        return sqlSession.selectList(queryId);
    }
     
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectList(queryId,params);
    }
	
    @SuppressWarnings("unchecked")
	public Object selectPagingList(String queryId, Object params){
    	printQueryId(queryId);
    	Map<String,Object> map = (Map<String,Object>)params;
    	
    	String strPageIndex = (String)map.get("page_index");
    	String strPageRow = (String)map.get("page_row");
    	int nPageIndex = 0;
    	int nPageRow = 5;
    	
    	if(StringUtils.isEmpty(strPageIndex)==false){
    		nPageIndex = Integer.parseInt(strPageIndex)-1;
    	}
    	if(StringUtils.isEmpty(strPageRow)==false){
    		nPageRow = Integer.parseInt(strPageRow);
    	}
    	map.put("start", (nPageIndex*nPageRow)+1);
    	map.put("end", (nPageIndex*nPageRow)+nPageRow);
    	
    	return sqlSession.selectList(queryId, map);
    }
}
