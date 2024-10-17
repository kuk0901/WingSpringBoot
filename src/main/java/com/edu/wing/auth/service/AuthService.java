package com.edu.wing.auth.service;

import java.util.List;

import com.edu.wing.auth.domain.AuthVo;

public interface AuthService {

	
	public AuthVo memberExist(String email, String password);
	public int memberInsertOne(AuthVo authVo);
	 // 이메일 중복 체크 메소드
    public Integer findByEmail(String email);
//	public AuthVo memberSelectOne(int no);
//	public int memberUpdateOne(AuthVo memberVo);
//	public int memberDeleteOne(int no);

}
