package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{
    //구현 객체에 의존적인 코드
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    //구현 객체에 의존하지 않고, 추상적인 인터페이스에만 의존한다.
    private final MemberRepository memberRepository;


    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();   1.
    //기존의 고정 할인가격 정책에서 ,다른 할인 정책으로 변경한다면

    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();   2.
    // OOP의 개방-폐쇄 원칙(OCP) :확장에는 열려 있지만 ,변경에는 닫혀있어야 함
    // -->Repository가 바뀌 었는데, 클라이언트(=사용자) 코드에서 변경해야 함 -->OCP 위반
    // 의존관계 역전 원칙(DIP) : 구현클래스에 의존하지 않고 , 인터페이스에 의존해야 함
    //-->구현하는 클래스가 무엇인지 파악하고 있어야 함(즉, 구현클래스에 의존함)

    //OCP와 DIP를 위반하지 않으려면  인터페이스에만 의존하도록 해야 하는데
    //구현체가 없어 코드를 실행 할 수 없어서 NPE 발생
    private DiscountPolicy discountPolicy;                              // 3.
    //-->누군가가 DiscountPolicy의 구현 객체를 대신 생성하고 ,주입해 줘야 한다.

    //성자를 통해서 어떤 구현 객체을 주입할지는 오직 외부(AppConfig)에서 결정
    //OrderServiceImpl은 실행에만 집중한다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}