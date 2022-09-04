package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1= ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2= ac.getBean(ClientBean.class);
        int count2 = clientBean1.logic();
        assertThat(count2).isEqualTo(1);
    }

    //프로토타입 빈 직접 요청
    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;

        public int getCount() {
            return count;
        }

        public void addCount(){
            count++;
        }

        @PostConstruct
        public void init(){
            System.out.println("prototype.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("prototype.destroy");
        }
    }

    //싱글톤 빈에서 프로토타입 빈 사용
    /**
     * ClientBean은 싱글톤, 보통 스프링 컨테이너 생성 시점에 함께 생성 & 의존관계 주입도 발생
     * 1. ClientBean은 주입시점에 스프링 컨테이너에 프로토타입 빈을 요청
     * 2. 스프링컨테이너는 프로토타입 빈을 생성해서 ClientBean에 반환 count = 0
     * 3. 클라이언트A가 logic을 호출
     * 4. ClientBean은 prototype.addCount()호출 count = 1
     * 5. 클라이언트B가 logic을 호출
     * 6. ClientBean은 prototype.addCount()호출 count = 2
     * b/c ClientBean이 내부에 가지고 있는 프로토타입 빈은 이미 과거에 주입이 끝난 빈.
     * 주입 시점에 스프링 컨테이너에 요청해서 프로토타입 빈이 새로 생성 된 것이지, 사용할 때마다 새로 생성되는 것은 아니다.
     */
    @Scope("singleton")
    static class ClientBean{
        //private PrototypeBean prototypeBean;

        //해결방법
        @Autowired
        //private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        //지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공하는 것
        //DI: 의존관계 주입(DI)가 아닌 직접 필요한 의존관계를 찾는 것(Dependency Lookup)
        //ObjectProvidier: ObjectFacotry를 상속. 스프링에 의존
        private Provider<PrototypeBean> prototypeBeanProvider;
        //별도의 라이브러리가 필요
        //자바표준이므로 스프링이 아닌 다른 컨테이너에서도 사용할 수 있다.

        public int logic(){
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }


        //해결방법: 싱글톤 빈이 프로토타입을 사용할 때 마다 스프링 컨테이너에 새로 요청하는 것
        //비추천: 스프링컨테이너에 종속적인 코드, 단위 테스트도 어려워짐
        /*
         * @Autowired
         * ApplicationContext applicationContext;
         *
         * public int logic(){
         * PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
         * prototypeBean.addCount();
         * int count = prototypeBean.getCount();
         * return count;
         * }
         */
    }
}
