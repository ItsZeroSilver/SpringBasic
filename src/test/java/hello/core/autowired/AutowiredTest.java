package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean{
        //자동 주입할 대상이 없으면 수정자 메서드 자체가 호출이 안
        @Autowired(required = false)
        public void setNoBean1(Member member1){
            System.out.println("member = " + member1);
        }
        //자동 주입할 대상이 비어있이면, Null로 반환
        @Autowired
        public void setNoBean2(@Nullable Member member2){
            System.out.println("member = " + member2);
        }
        //자동 주입할 대상이 비어있이면, Optional.empty로 반환
        @Autowired
        public void setNoBean3(Optional<Member> member3){
            System.out.println("member = " + member3);
        }
    }
}
