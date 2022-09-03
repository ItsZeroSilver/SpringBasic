package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig{
        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
    /**
     * 생성자 호출, url = null
     * connect: null
     * call: null message: 초기화 연결 메세지
     * 생성자 부분을 보면 url정보 없이 connect가 호출
     * 객체를 생성하는 단계에서는 url이 없고, 객체를 생성한 다음에 외부에서 수정자 주입을 통해서 setUrl()이 호출되어야 url이 존재
     *
     * 스프링빈은 "객체 생성" -> "의존관계 주입"
     * 따라서 초기작업은 의존관계 주입이 모두 완료되고 난 후 호출
     * 스프링은 의존관계 주입이 완료되면 스프링 빈에서 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공
     * 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다.
     *
     * 스프링 빈의 이벤트 라이프사이클
     * 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료
     * 초기화 콜백: 빈 생성 & 의존관계 주입 완료
     * 소멸전 콜백: 빈의 소멸되기 직전에 호출
     *
     * 참고: 객체의 생성가 초기화를 분리하자.
     * 유지보수 관점필요.
     * 객체의 생성은 파라미터 정보를 받고, 메모리를 할당 받아 객체를 생성하며, 초기화는 생성된 값을 활용하여 외부 커넥션을 연결하는 무거운 동작을 수행
     *
     * 빈 생명주기 콜백
     * 1. InitializingBean, DisposableBean
     * 생성자 호출, url = null
     * connect: null
     * call: null message:초기화 연결 메세지
     * NetworkClient.afterPropertiesSet
     * connect: http://hello-spring.dev
     * call: http://hello-spring.dev message:초기화 연결 메시지
     * 22:50:27.645 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@d9345cd, started on Sat Sep 03 22:50:27 KST 2022
     * close: http://hello-spring.dev
     * 이 인터페이스는 스프링 전용 인터페이스로 해당 코드가 스프링 전용 인터페이스에 의존.
     * 초기화,소멸 메서드의 이름을 변경할 수 없다.
     * 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
     *
     * 2. 빈 등록 초기화, 소멸 메서드
     * @Bean(initMethod =  "init", destroyMethod = "close")
     * 성자 호출, url = null
     * connect: null
     * call: null message: 초기화 연결 메세지
     * NetworkClient.init
     * connect: http://hello-spring.dev
     * call: http://hello-spring.dev message: 초기화 연결 메시지
     * 23:00:03.148 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@d9345cd, started on Sat Sep 03 23:00:02 KST 2022
     * NetworkClient.close
     * close: http://hello-spring.dev
     *
     * 메서드 이름을 자유롭게 줄 수 있다.
     * 스프링빈이 스프링 코드에 의존하지 않다.
     * 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
     * 종료 메서드 호출시 기본값이 (inferred) 추론으로 등록되어 있다.
     * 이 추론 기능은 close, shutdown이라는 이름의 메서드를 자동으로 호출한다.
     * 만약 추론기능을 사용하기 싫으면 destoryMethod = ""처럼 빈 공백을 지정하면 된다.
     *
     * 3. 애노테이션 @PostConstruct, @PreDestory
     * 생성자 호출, url = null
     * connect: null
     * call: null message: 초기화 연결 메세지
     * NetworkClient.init
     * connect: http://hello-spring.dev
     * call: http://hello-spring.dev message: 초기화 연결 메시지
     * 23:09:41.493 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@d9345cd, started on Sat Sep 03 23:09:41 KST 2022
     * NetworkClient.close
     * close: http://hello-spring.dev
     * 권장
     * 스프링이 아닌 다른 컨테이너에서도 잘 동작한다.
     * 외부 라이브러리에는 적용을 하지 못한다.
     */
}
