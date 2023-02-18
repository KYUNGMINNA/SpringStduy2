package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**  BeanLifeCycleTest 파일에서 테스트 */
public class NetworkClient{
/**   1.빈 생명 주기 콜백 -인터페이스 방식 ( 오래 된 방식)
** implements InitializingBean, DisposableBean{
*/
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 , url=" + url);
        connect();
        call("초기화 연결 메세지");
    }

    public void call(String msg) {
        System.out.println("call= " + url + " message= " + msg);
    }

    public void connect() {
        System.out.println("connect= " + url);
    }

    public void disconnect() {
        System.out.println("close= " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

/**
 *  1.빈 생명 주기 콜백 -인터페이스 방식 ( 오래 된 방식)
    // DisposableBean
    // 빈이 종료될 때 호출된다.
    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
    // InitializingBean
    // 프로퍼티가 세팅이 끝나면 = 의존 관게 주입이 끝나면 호출한다.
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }
*/


/**
 *  2. 빈 생명주기 콜백 - 메서드 지정 방식
    // 메서드 이름을 임의로 변경한다.
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
*/

/**
 * 3. 빈 생명주기 콜백 - 애너테이션 방식
 * */
@PostConstruct
public void init() throws Exception {
    connect();
    call("초기화 연결 메시지");
}

    @PreDestroy
    public void close() throws Exception {
        disconnect();
    }
}