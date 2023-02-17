package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//순후 자바로 쓰다가 스프링으로 변환하기
@Configuration
public class AppConfig {
//구현 객체를 생성하고 ,연결하는 책임을 담당
//클라이언트 객체는 단순히 실행하는 책임만 담당
//-->이렇게 단일 책임 원칙을 따르며 ,관심사를 분리 함


    //MemoryMemberRepository 객체를 생성하고 , 그 참조값울 생성하면서
    //MemberServiceImpl의 생성자의 파라미터로 전달한다.

    /*
    public MemberService memberService(){
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService(){
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
     */

    /**
     *  중복을 제거하고 , 역할에 따른 구현이 보이도록 리팩터링 해보자 .
     *
     *  + 스프링으로 전환하기
     * */

    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    //MemoryMemberRepository를 다른 구현체로 변경할 때 한 부분만 변경하면 된다.
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    // 할인 정책을 변경하게 된다면
    //할인 정책을 변경해도, 애플리케이션의 구성 역할을 담당하는 AppConfig만 변경하면 된다.
    // 클라이언트 코드인 OrderServiceImpl을 포함해서 사용 영역의 어떤 코드도 변경할 필요가 없다.
    @Bean
    public DiscountPolicy discountPolicy(){
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}