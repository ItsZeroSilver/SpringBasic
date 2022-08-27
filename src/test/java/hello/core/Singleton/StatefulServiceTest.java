package hello.core.Singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1= ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2= ac.getBean("statefulService", StatefulService.class);

        //TreadA: A사용자 1000원 주문
        //statefulService1.order("userA", 10000);
        int price1 = statefulService1.order("userA",10000);
        //TreadB: B사용자 2000원 주문
        //statefulService2.order("userB", 20000);
        int price2 = statefulService2.order("userB", 20000);

        //TreadA: 사용자A 주문 금액 조회
//        int price = statefulService1.getPrice();//ThreadA, ThreadB는 같은 객체를 쓰므로 20000원이 됌
//        System.out.println("price = " + price);
        System.out.println(price1);
        //공유필드는 조심해야하며, 스프링은 항상 무상태로 설계해야한다.
//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        Assertions.assertThat(price1).isEqualTo(10000);
    }
    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}