package com.birdhill.user.service;


import com.birdhill.user.vo.UserVO;

import java.util.List;
import java.util.Map;

public interface UserService {

    void insertUser(UserVO user) throws Exception;

    UserVO selectUser(Map<String,Object> map) throws  Exception;

    void updateUser(UserVO user) throws Exception;

    List<Map<String,Object>> selectUserList(Map<String, Object> map) throws Exception;

    void deleteUser(Map<String, Object> map)  throws Exception;

    boolean isRegistedLoginId(String loginId) throws Exception;
}
