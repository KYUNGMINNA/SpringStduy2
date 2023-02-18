package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // 사용자가 만든 어노테이션이 부착될 수 있는 타입을 지정
@Retention(RetentionPolicy.RUNTIME) //에노테이션의 라이프사이클 종료시점 설정
@Documented //javadocs에 어노테이션의 사용을 문서화 해줌
public @interface MyIncludeComponent {
}
