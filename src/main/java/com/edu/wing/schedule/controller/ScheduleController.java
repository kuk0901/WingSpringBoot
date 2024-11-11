package com.edu.wing.schedule.controller;
import com.edu.wing.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleController {

    @Autowired
    private MemberService memberService;  // 인터페이스 타입으로 주입받음

    // 매달 15일 자정에 실행
    @Scheduled(cron = "0 0 0 15 * ?")
    public void scheduleMemberCleanup() {
        memberService.deleteInactiveMembers();
        System.out.println("Scheduled member cleanup executed on the first of the month.");
    }


}
