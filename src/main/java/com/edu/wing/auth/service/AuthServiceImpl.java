package com.edu.wing.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.wing.auth.dao.AuthDao;
import com.edu.wing.auth.domain.AuthVo;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	public AuthDao authDao;
	
	

	@Override
	public AuthVo memberExist(String email, String password) {
		// TODO Auto-generated method stub
		return authDao.memberExist(email, password);
	}



	@Override
	public int memberInsertOne(AuthVo authVo) {
		// TODO Auto-generated method stub
		return authDao.memberInsertOne(authVo);
	}



	@Override
	public boolean isEmailAlreadyRegistered(String email) {
		AuthVo authVo = authDao.selectMemberByEmail(email);
		return authVo != null;  // 이미 가입된 이메일이면 true 반환
	}
	
//	@Override
//	public int memberInsertOne(MemberVo memberVo) {
//		// TODO Auto-generated method stub
//		return memberDao.memberInsertOne(memberVo);
//	}
//
//	@Override
//	public MemberVo memberSelectOne(int no) {
//		// TODO Auto-generated method stub
//		return memberDao.memberSelectOne(no);
//	}
//
//	@Override
//	public int memberUpdateOne(MemberVo memberVo) {
//		// TODO Auto-generated method stub
//		return memberDao.memberUpdateOne(memberVo);
//	}
//	
//	@Override
//	public int memberDeleteOne(int no) {
//		// TODO Auto-generated method stub
//		return memberDao.memberDeleteOne(no);
//	}
//
//	public List<MemberVo> memberSelectList(String searchOption, String keyword, String orderBy, String sortDirection) {
//	    Map<String, Object> searchMap = new HashMap<>();
//	    searchMap.put("keyword", keyword);
//	    searchMap.put("searchOption", searchOption);
//	    searchMap.put("orderBy", orderBy);           // 정렬 기준 추가
//	    searchMap.put("sortDirection", sortDirection); // 정렬 방향 추가
//
//	    return memberDao.memberSelectList(searchMap);
//	}
//	public List<MemberVo> memberSelectList(String searchOption, String keyword) {
//	    // 검색만 처리하는 메서드
//	    Map<String, Object> searchMap = new HashMap<>();
//	    searchMap.put("keyword", keyword);
//	    searchMap.put("searchOption", searchOption);
//
//	    return memberDao.memberSelectList(searchMap);
//	}
//	
}
