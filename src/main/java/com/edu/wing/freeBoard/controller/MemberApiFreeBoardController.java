package com.edu.wing.freeBoard.controller;

import com.edu.wing.freeBoard.service.FreeBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/api/freeBoard")
public class MemberApiFreeBoardController {

  private static final Logger log = LoggerFactory.getLogger(MemberApiFreeBoardController.class);
  private static final String LOG_TITLE = "==MemberApiFreeBoardController==";

  @Autowired
  private FreeBoardService freeBoardService;

}
