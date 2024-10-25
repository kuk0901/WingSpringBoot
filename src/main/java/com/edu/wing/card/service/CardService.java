package com.edu.wing.card.service;

import com.edu.wing.card.domain.CardVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CardService {
  List<CardVo> cardSelectList(int start, int end, String categoryName);
  int cardSelectTotalCount(String categoryName);
  CardVo cardSelectOne(int cardNo);
  boolean cardExist(String cardName);
  int insertCardAndBenefits(Map<String, String> formData, MultipartFile file, String benefitsJson);
}
