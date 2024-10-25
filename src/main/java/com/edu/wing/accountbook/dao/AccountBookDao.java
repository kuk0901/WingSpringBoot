package com.edu.wing.accountbook.dao;

import com.edu.wing.accountbook.domain.AccountBookVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccountBookDao {



    List<AccountBookVo> selectAccountBook();
    List<AccountBookVo> selectAccountBooks(Map<String, Object> params);
    List<String> selectPlusCategories();
    List<String> selectMinusCategories();
    List<String> selectPaymentMethods();

    List<AccountBookVo> getTopPaymentMethods(); // 상위 3개 결제 방법 조회
    List<AccountBookVo> getTopCategories();
    void accountBookDelete(int memberNo); // 회원 번호로 가계부 삭제 메서드
    //유저전용영역
    List<AccountBookVo> selectAccountBookByMonth(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("memberNo") int memberNo,
            @Param("limit") int limit //
    );
    List<AccountBookVo> selectAccountBookByRecentDate(Map<String, Object> params);
    //초기화면용 가계부
    int selectMonthlyEntryCount(Map<String, Object> params);//월간내역수
    // 월별 가계부 내역 가져오기
    List<AccountBookVo> getMonthlyEntries(@Param("memberNo") int memberNo,
                                          @Param("startDate") String startDate,
                                          @Param("endDate") String endDate);
    int insertAccountBook(Map<String, Object> params); // 가계부 추가 메서드
        
    AccountBookVo selectAccountBookDetail(@Param("accountBookNo") int accountBookNo, @Param("memberNo") int memberNo);
    //가계부상세조회
}
