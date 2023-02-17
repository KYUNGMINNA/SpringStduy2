package hello.core;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI컨테이너")
    void pureContatiner(){
        AppConfig appConfig=new AppConfig();

        //1.조회 호출할 때 마다 객체를 생성 : 매번 새 객체 생성해서 메모리 낭비 이슈
        MemberService memberService1=appConfig.memberService();
        MemberService memberService2=appConfig.memberService();

        //참조 값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용 ")
    void singletonServiceTest(){

        //조회 : 싱글톤 패턴이라 같은 객체
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
        /**
         * isSameAs는 ==연산자와 동일 ,  isEqualsTo가 equals연산자와 동일
         */

    }
}
