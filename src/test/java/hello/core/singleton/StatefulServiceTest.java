package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac=new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);
        
        statefulService1.order("userA",10000);
        
        statefulService2.order("userB",20000);

        System.out.println("statefulService1.getPrice() = " + statefulService1.getPrice());
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        //isSameTO는 주소값을 비교 !
        //대상의 내용자체를 비교


    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}