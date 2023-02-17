package hello.core.member;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args){
        //관심사의 분리를 위해 AppConfig를 만들었다.
        //AppConfig appConfig = new AppConfig();

        //더 나아가 스프링으로 변환한다
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);
        //스프링 컨테이너에 AppConfig 설정파일을 사용한다.
        //AppConfig 파일에서 @Bean 붙은 메서드들을 빈으로 등록한다.
        // 메서드명이 "빈이름" , 반환되는 값이 "타입"



        //MemberService memberService = appConfig.memberService();
        //스프링으로 변환한다
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);


        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}