package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component

/**  스코프와 Provider  v1 */
//@Scope(value = "request")

/**  스코프와 프록시 v2 */
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)

/**
 * @Scope의 proxyMode = ScopedProxyMode.TARGET_CLASS를 설정하면 스프링 컨테이너는
 * CGLIB이라는 바이트 코드 조작 라이브러리로 MyLogger를 상속 받은 가짜 프록시 객체를 생성한다.
 * ac.Bean("myLoger", MyLogger.class)로 찍어봐도 프록시 객체가 조회된다.
 */
public class MyLogger {
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        String format = String.format("[%s][%s]%s", uuid, requestURL, message);
        System.out.println(format);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println(String.format("[%s]request scope bean create:", uuid) + this);
    }

    @PreDestroy
    public void destroy() {
        System.out.println(String.format("[%s]request scope bean close:", uuid) + this);
    }
}