package com.edu.wing.member.dao;

import com.edu.wing.member.domain.MemberVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class MemberDaoImpl implements MemberDao {

  @Autowired
  private SqlSession sqlSession;

  private final String namespace = "com.edu.wing.member.";

  @Override
  public MemberVo memberExist(String email, String pwd) {
    HashMap<String, String> map = new HashMap<>();
    map.put("email", email);
    map.put("pwd", pwd);

    return sqlSession.selectOne(namespace + "memberExist", map);
  }

  @Override
  public MemberVo memberSelectOne(String email) {
    return sqlSession.selectOne(namespace + "memberSelectOne", email);
  }

  @Override
  public int memberInsertOne(MemberVo memberVo) {
    return sqlSession.insert(namespace + "memberInsertOne", memberVo);
  }

  @Override
  public MemberVo selectMemberByEmail(String email) {
    return sqlSession.selectOne(namespace + "selectMemberByEmail", email);
  }
}
