package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonWithPrototypeTest1 {

    
    
    /**   v1,v2,v3 전용         */
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        // ClientBean, PrototypeBean 둘 다 컴포넌트 스캔을 해줘야 빈으로 등록된다.
        ApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);

        /**
         *
         */
    }
    @Scope("singleton")
    static class ClientBean {
  /**       생성 시점에 이미 주입되어 ,프로토타입의 빈이라도 계속 같은 걸 쓰게 된다.*/
//        private final PrototypeBean prototypeBean;
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
//        public int logic() {
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }

        /**  싱글톤 빈과 혼용하더라도 프로토타입을 매번 새롭게 생성하면서 사용하는 방법  v1*/
//        @Autowired
//        private ApplicationContext ac;
//        public int logic() {
//            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }
        /** 의존 관계를 외부에서 주입받는게 아니라 ,직접 필요한 의존관계를 찾는 방식인
         *  Dependency Lookup : 의존관계 조회(탐색) 이다.
         *  스프링의 컨텍스트 전체를 주입 받게 되어 ,스프링 컨테이너와 종속성이 생기고 테스트도 어려움
         *  */


        /**싱글톤 빈과 혼용하더라도 프로토타입을 매번 새롭게 생성하면서 사용하는 방법 개선 버전 v2
         * ObjectFactory: 지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공해준다.
         * 아주 단순하게 getObject 하나만 제공하는 FunctionalInterface이고, 별도의 라이브러리도 필요 없다.
         * 그리고 스프링에 의존한다.
         *
         * ObjectProvider :ObjectFactory에 편의기능들(Optional, Stream...)추가해서 만들어진 객체
         * 별도의 라이브러리는 필요 없고 스프링에 의존한다.
         *
         *  결국 둘다 스프링에 의존적이게 된다 !
         * */
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//
//        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }
        /**  스프링에 의존적이지 않고 , 자바 표준을 이용하는 방법 v3
         *  자바 표준이고 기능이 단순해 단위 테스트 가능
         *
         *  스프링이 아닌 다른 컨테이너에서도 사용 가능  but 별도의 라이브러리 필요
         *
         *  특별한 이유 없다면 스프링 제공하느 기능을 사용하고
         *  다른 컨테이너에게 호환되려면 자바 표준을 사용
         *
         * */
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }


        /**
         * 프로토 타입의 빈을 언제 사용해야 하는가
         * - 여러 인스턴스를 검색해야 하는 경우
         * - 인스턴스를 지연 혹은 선택적으로 찾아야 하는 경우
         * - 순환 종속성을 깨기 위해서
         * - 스코프에 포함된 인스턴스로부터 더 작은 범위의 인스턴스를 찾아 추상화 하기 위해서 사용한다.
         */


    }
    @Scope("prototype")
    static class PrototypeBean {


        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init: " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy" + this);
        }
    }

    /**
     * 싱글톤은 스프링 컨테이너와 생명주기를 같이하지만 ,프로토타입 스프링 빈은 생명주기를 달리 한다.
     *
     * 싱글톤 스프링 빈은 매번 스프링 컨테이너에 동일한 인스턴스를 반환한다.
     *
     * 프로토타입 스프링 빈은 컨테이너에 요청할 때 마다 새로운 스프링 빈을 생성한 후
     * 의존 관계 주입 및 초기화 진행 후 반환한다.
     *
     * 프로토타입  스프링 빈은 소멸 메서드가 호출되지 않는다
     * -->클라이언트가 프로토타입 스프링 빈은 직접 관리해야한다(소멸 메서드도 직접 호출 해야 함)
     *
     *  스프링 컨테이너 생성
     *  1.스프링 컨테이너 생성 -> 2.스프링 빈 등록 -> 3.스프링 빈 의존관계 설정
     *
     *
     */
}