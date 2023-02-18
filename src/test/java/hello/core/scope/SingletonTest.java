package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonTest {

    @Test
    void singletonBeanFind() {
        // 빈을 직접 넣어주면 해당 빈을 자동으로 컴포넌트 스캔해서 빈으로 등록한다
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);

        assertThat(singletonBean1).isSameAs(singletonBean2);
        ac.close();
    }


    // 그래서 여기에 @Component가 없어도 스프링 빈으로 등록되는 것이다.
    @Scope("singleton")
    static class SingletonBean {
        
        public SingletonBean(){
            System.out.println("생성자 메서드");
        }
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("SingletonBean.close");
        }
    }
    /**
     *    스프링 빈의 이벤트 라이프 사이클
     *    스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용-> 소멸전 콜백 ->스프링 종료
            초기화 콜백 : 빈이 생성되고 ,빈이 의존관계 주입 완료된 후 호출
            소멸전 콜백 : 빈이 소멸되기 직전에 호출

     */
}