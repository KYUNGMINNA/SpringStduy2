package hello.core.scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// 빈 스코프 컴포넌트 스캔 자동 등록
@Scope("prototype")
@Component
public class PrototypeBean {

}

/**  빈 스코프 수동 등록*/
//public class PrototypeBean{
//    @Scope("prototype")
//    @Bean
//    PrototypeBean HelloBean() {
//        return new PrototypeBean();
//    }
//}