package com.edu.wing.sellingCard.service;

import com.edu.wing.sellingCard.dao.SellingCardDao;
import com.edu.wing.sellingCard.domain.SellingCardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SellingCardServiceImpl implements SellingCardService {

  @Autowired
  private SellingCardDao sellingCardDao;

  @Override
  public List<SellingCardVo> sellingCardSelectList(int start, int end, int cardNo) {
    return sellingCardDao.sellingCardSelectList(start, end, cardNo);
  }

  @Override
  public int sellingCardSelectTotalCount(int cardNo) {
    return sellingCardDao.sellingCardSelectTotalCount(cardNo);
  }

  @Override
  public Map<String, Object> sellingCardSelectOne(int sellingCardNo) {
    return sellingCardDao.sellingCardSelectOne(sellingCardNo);
  }

  @Override
  public List<Map<String, Object>> sellingCardSelectOneForUserPage(int memberNo) {
    return sellingCardDao.sellingCardSelectOneForUserPage(memberNo);
  }
}
