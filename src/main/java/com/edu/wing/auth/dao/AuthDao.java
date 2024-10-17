package com.edu.wing.auth.dao;

import java.util.List;
import java.util.Map;

import com.edu.wing.auth.domain.AuthVo;

public interface AuthDao {

//	List<MemberVo> memberSelectList(Map<String, Object> map); 
	
	public AuthVo memberExist(String email, String password);
	public  int memberInsertOne(AuthVo authVo);
	AuthVo selectMemberByEmail(String email);
//	public MemberVo memberSelectOne(int no);
//	public int memberUpdateOne(MemberVo memberVo);
//	public int memberDeleteOne(int no);
}
