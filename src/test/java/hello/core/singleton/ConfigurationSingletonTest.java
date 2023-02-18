package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){
        ApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);

        //테스트 용도를 위해 구체 클래스를 불러오지만, 지양해야 한다.
        MemberServiceImpl memberService=ac.getBean("memberService",MemberServiceImpl.class);
        OrderServiceImpl orderService=ac.getBean("orderService", OrderServiceImpl.class);

        //저장소 자체 확인
        MemberRepository memberRepository=ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1= memberService.getMemberRepository();
        MemberRepository memberRepository2= memberService.getMemberRepository();

        System.out.println(" memberRepository1  =" +    memberRepository1);
        System.out.println(" memberRepository2 ="  +    memberRepository2);
        System.out.println(" memberRepository  = " +    memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // config로 넘긴 클래스도 스프링 빈으로 등록이 된다.
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean.getClass() = " + bean.getClass());
    }
}
