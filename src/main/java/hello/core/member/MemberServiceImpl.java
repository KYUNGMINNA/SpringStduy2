package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
    //MemberRepository 인터페이스에만 의존하게 된다.

    //생성자를 통해 어던 구현 객체를 주입 할 것인지는 외부(AppConfig)에서 결정
    //의존 관계에 대한 고민은 외부에 맡기고 실행에만 집중한다.
    @Autowired //스프링이 의존관계를 주입해주기 위한 애너테이션
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }
    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }


    //테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

}
