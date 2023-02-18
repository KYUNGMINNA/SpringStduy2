package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        // 빈을 직접 넣어주면 해당 빈을 자동으로 컴포넌트 스캔해서 빈으로 등록한다
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
                PrototypeBean.class);

        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
        ac.close();
    }

    // 그래서 여기에 @Component가 없어도 스프링 빈으로 등록되는 것이다.
    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
        //클라이언트에 빈을 반환환 뒤 ,스프링 컨테어느는 생성된 프로토타입의 빈을 관리하지 않음
        // 관리 책임은 클라이언트에게 있어서 ,  @PreDestroy 같은 종료 메서드 호출 안됨
        // 사용하려면 직접 종료 메서드 호출해야 함 .
    }
    /**
     *         프로토 타입의 scope 빈의 요청
     *         1.스프링 컨테이너에게 prototype 빈 요청
     *         2.스프링 컨테이너는 요청 받은 시점에 프로토타입 빈 생성  후 의존 관계 주입
     *         3.스프링 컨테이너는 생성한 프로토타입 빈을 클라이언트에 전달
     *         4.이후 같은 요청이 오면 항상 새로운 프로토타입 빈을 생성해 반환
     *      -->스프링 컨테이터가 프로토타입의 빈을 생성하고, 의존관계를 주입하고, 초기화 하는것 까지만 처리함 !!
     *       :: 프로토 타입의 빈일 때만
     *
     *  프로토타입은 싱글톤과 다르게 빈 생성 , 의존관계 주입 , 초기화 까지만 진행
     * 이후 스프링 빈을 클라이언트에게 반환한 이후로 관리하지 않기 때문에 소멸 메서드같은것은 모두
     * 클라이언트가 자체적으로 관리해야함 .
     */

}