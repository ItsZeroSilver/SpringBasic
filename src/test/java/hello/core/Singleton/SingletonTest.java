package hello.core.Singleton;

import hello.core.member.MemberService;
import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        //1.조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        //2.조회: 호출할 때마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();
        //참조값이 다른 것을 확인
        System.out.println("memberService1 =" + memberService1);
        System.out.println("memberService2 =" + memberService2);
        //결과가 달라야함
        //순수한 DI컨테이너인 AppConifg는 요청을 할 때 마다 객체를 새로 생성한다.
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용하여 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println(singletonService1);
        System.out.println(singletonService2);//객체 값이 같은 것을 확인

        assertThat(singletonService1).isSameAs(singletonService2);
        //same = instance 참조 비교
        //equal - java 비교
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱톤")
    void springContainer() {
        //스프링 컨테이너 : 고객이 요청이 들어올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유해서 효율적으로 사
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        //1.조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        //2.조회: 호출할 때마다 객체를 생성
        //참조값이 다른 것을 확인
        System.out.println("memberService1 =" + memberService1);
        System.out.println("memberService2 =" + memberService2);
        //결과가 달라야함ㅁ
        //순수한 DI컨테이너인 AppConifg는 요청을 할 때 마다 객체를 새로 생성한다.
        assertThat(memberService1).isSameAs(memberService2);
    }
}