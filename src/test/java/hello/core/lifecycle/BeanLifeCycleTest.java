package hello.core.lifecycle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        /** 1.1.빈 생명 주기 콜백 -인터페이스 방식 ( 오래 된 방식)
         * @Bean  */

        /**. 2.빈 생명주기 콜백 - 메서드 지정 방식
        // 각 단계에서 호출할 메서드의 이름을 넣어준다.
        @Bean(initMethod = "init", destroyMethod = "close")
        */

        /**
         * 3. 빈 생명주기 콜백 - 애너테이션 방식
         */
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");

            return networkClient;
        }
    }


    /**
     *  스프링 빈의 라이프사이클
     *  스프링 컨테이너 생성 ->스프링 빈 생성[객체 생성]->의존관계 주입 -> 초기화 콜백
     *  -> 사용 -> 소멸전 콜백 ->스프링 종료->스프링 컨테이너생성
     */
}
