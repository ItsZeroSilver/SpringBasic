package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {
    @Test
    void prototypeBeanTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        //종료 메서드를 직접 수작업
        prototypeBean1.destroy();
        prototypeBean2.destroy();
        ac.close();
        /**
         * 결과
         * find prototypeBean1
         * PrototypeBean.init
         * find prototypeBean2
         * PrototypeBean.init
         * prototypeBean1 = hello.core.scope.PrototypeTest$PrototypeBean@673be18f
         * prototypeBean2 = hello.core.scope.PrototypeTest$PrototypeBean@6581dc0a
         * close가 되지 않음
         *
         * 싱글톤 빈은 스프링 컨테이너 생성 시점에 초기화 메서드가 실행
         * 프로토타입 스코프의 빈은 스프링 컨테이너에서 빈을 조회할 때 생성되고, 초기화 메서드에서도 실행된다.
         * 프로토타입 빈을 2번 조회했으므로 완전히 다른 스프링 빈이 생성되고, 초기화도 2번 실행되는 것을 확인할 수 있다.
         * 싱글톤 빈ㄴ은 스프링 컨테이너가 관리하기 때문에 스프링 컨테이너가 조료될 때 빈의 종료 메서드가 실행.
         * 프로토타입 빈은 스프링 컨테이너가 생성과 의존주입, 초기화까지만 관여. 따라서 @PreDestory와 같은 종료 메서드가 실행되지 않는다.
         * 그래서 프로토타입 빈은 프로토타입 빈을 조회한 클라이언트가 관리해야한다. 종료 메서드에 대한 호출도 클라이언트가 직접 해야한다.
         *
         */
    }

    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
