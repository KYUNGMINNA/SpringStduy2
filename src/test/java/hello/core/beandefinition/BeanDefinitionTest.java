package hello.core.beandefinition;

import hello.core.discount.DiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {
   // AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);
    GenericXmlApplicationContext ac=new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타 정보 확인")
    void findAPplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefinitionName = " + beanDefinitionName+",   beanDefinition="+beanDefinitionName);
            }
        }
    }

//    @Autowired
//    private DiscountPolicy discountPolicy;  --> fix,rate 두 개의 빈이 발견되어 오류
    @Autowired // 1. 타입으로 매칭을 시도하고 매칭결과가 2개 이상인경우 2.빈이름으로 매칭을 시도
    private DiscountPolicy rateDiscountPolicy;



}
