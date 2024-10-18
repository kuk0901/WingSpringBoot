package com.edu.wing.minusCategory.controller;


import com.edu.wing.minusCategory.domain.MinusCategoryVo;
import com.edu.wing.minusCategory.service.MinusCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/minusCategory")
public class AdminMinusCategoryController {

    private Logger log = LoggerFactory.getLogger(AdminMinusCategoryController.class);
    private final String logTitleMsg = "==AdminMinusCategoryController==";

    @Autowired
    private MinusCategoryService minusCategoryService;

    @GetMapping(value = "/list")
    public ModelAndView minusCategoryList(Model model) {

        log.info(logTitleMsg);

        List<MinusCategoryVo> minusCategoryList = minusCategoryService.minusCategorySelectList();

        log.info("Retrieved minus categories: {}", minusCategoryList.size());

        ModelAndView mav = new ModelAndView("jsp/category/minusCategory/MinusCategoryView");

        return mav;
    }
}
