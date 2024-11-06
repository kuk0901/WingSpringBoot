package com.edu.wing.dashboard;

import com.edu.wing.sellingCard.service.SellingCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
  @Autowired
  SellingCardService sellingCardService;

  @GetMapping("/card")
  public ModelAndView card() {
    ModelAndView mav = new ModelAndView("jsp/admin/dashboard/DashboardView");

    ObjectMapper objectMapper = new ObjectMapper();

    try {
      String jsonTotalCardsData = objectMapper.writeValueAsString(sellingCardService.totalCardsSoldLast5Years());
      String jsonRecommendedCardsData = objectMapper.writeValueAsString(sellingCardService.recommendedCardsPurchasedLast5Years());
      String jsonTerminatedCardsData = objectMapper.writeValueAsString(sellingCardService.terminatedCardsLast5Years());

      mav.addObject("totalCardsData", jsonTotalCardsData);
      mav.addObject("recommendedCardsData", jsonRecommendedCardsData);
      mav.addObject("terminatedCardsData", jsonTerminatedCardsData);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return mav;
  }
}
