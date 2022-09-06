package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)//HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나느 시점에 소멸
//적용 대상이 인터페이가 아닌 클래스면 TARGET_CLASS출력
//적용 대상이 인터페이스면 INTERFACES를 선택
public class MyLogger {
    private String uuid;
    private String requestURL;

    //requestURL은 생성되는 시점을 알 수가 없음.
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("["+ uuid +"] "+ "["+ requestURL + "] " + message);
    }

    //uuid 생정 및 저장
    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("["+ uuid +"] request scope bean create: " + this);
    }

    @PreDestroy
    public void close(){
        System.out.println("["+ uuid +"] request scope bean close: " + this);
    }
}
