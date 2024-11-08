package com.edu.wing.member.controller;

import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.cardBenefit.service.CardBenefitService;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import com.edu.wing.sellingCard.service.SellingCardService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequestMapping("/member/user")
@Controller
public class MemberUserController {
    private Logger log = LoggerFactory.getLogger(MemberUserController.class);
    private final String logTitleMsg = "==MemberUserController==";

    @Autowired
    private MemberService memberService;
    @Autowired
    private SellingCardService sellingCardService;
    @Autowired
    private CardBenefitService cardBenefitService;
    // 마이페이지 화면 이동
    @RequestMapping("/myPage")
    public ModelAndView myPage(HttpSession session) {
        log.info(logTitleMsg + " 마이페이지로 이동");

        ModelAndView mav = new ModelAndView("jsp/member/user/UserMyPageView");

        MemberVo currentMember = (MemberVo) session.getAttribute("member");

        int memberNo = currentMember.getMemberNo();
        MemberVo memberVo = memberService.getMyPageInfo(memberNo);


        if ("Y".equals(memberVo.getProductPurchase())){
            Map<String, Object> sellingCard = sellingCardService.sellingCardSelectOneForUserPage(memberNo);
            mav.addObject("sellingCard",sellingCard);
            int cardNo = ((BigDecimal) sellingCard.get("CARDNO")).intValue();
            List<CardBenefitVo> benefits = cardBenefitService.cardBenefitSelectListOne(cardNo);
            mav.addObject("benefits",benefits);
        }

        mav.addObject("memberVo",memberVo);

        return mav;
    }

}
