package hello.core.web;

import hello.core.common.MyLogger;
import hello.core.logdemo.LogDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;


    /**  스코프와 Provider v1
     * myLogger를 찾을 수 있는, DL할 수 있는 객체가 주입되어 주입 시점에 myLogger을 주입받을 수 있다.
     * */
//    private final ObjectProvider<MyLogger> myLoggerProvider;
//    @RequestMapping("log-demo")
//    @ResponseBody
//    public String logDemo(HttpServletRequest request) {
//        String requestURL = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();
//        myLogger.setRequestURL(requestURL);
//
//        myLogger.log("controller test");
//        logDemoService.logic("testId");
//        return "OK";
//    }


    /**  스코프와 프록시 v2 */
    private final MyLogger myLogger;
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        System.out.println("myLogger = " + myLogger.getClass());


        return "OK";
    }

    /**
     *  @Scope의 proxyMode = ScopedProxyMode.TARGET_CLASS를 설정하면 스프링 컨테이너는
     *  CGLIB이라는 바이트 코드 조작 라이브러리로 MyLogger를 상속 받은 가짜 프록시 객체를 생성한다.
     *  ac.Bean("myLoger", MyLogger.class)로 찍어봐도 프록시 객체가 조회된다.
     *
     *  가짜 프록시 객체는 요청이 오면 그때가 돼서야 내부에서 진짜 빈을 요청하는 위임 로직을 실행한다.
     * 1.가짜 프록시 객체는 내부에 진짜 myLogger를 찾는 방법을 알고 있다.
     * 2.클라이언트가 myLogger.logic()을 호출하면 가짜 프록시 객체의 메서드가 호출된다.
     * 3.가짜 프록시 객체는 request 스코프의 진짜 myLogger.logic()을 호출한다.
     * 4.가짜 프록시 객체는 원본 클래스를 상속받아 만들어졌기 때문에 사용하는 클라이언트 객체는 원본이든 아니든 다형성을 활용해 동일하게 사용한다.
     *
     */


}