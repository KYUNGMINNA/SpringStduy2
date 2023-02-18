package hello.core.logdemo;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    /**  스코프와 Provider v1*/
//    private final ObjectProvider<MyLogger> myLoggerProvider;
//    public void logic(String testId) {
//        MyLogger myLogger = myLoggerProvider.getObject();
//        myLogger.log("service id=" + testId);
//    }


    /**  스코프와 프록시 v2 */
    private final MyLogger myLogger;
    public void logic(String id) {
        myLogger.log("service id = " + id);
    }

}