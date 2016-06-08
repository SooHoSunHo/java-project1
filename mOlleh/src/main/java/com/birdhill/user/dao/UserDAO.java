package com.birdhill.user.dao;

import com.birdhill.common.dao.AbstractDAO;
import com.birdhill.user.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("userDAO")
public class UserDAO extends AbstractDAO {

    /*
    public void insertUser(UserVO user) throws Exception{
        insert("user.insertUser", user);
    }
	*/

    public UserVO selectUser(Map<String, Object> map) throws Exception {
        return (UserVO) selectOne("user.selectUser",map);
    }



}
