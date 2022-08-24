package hello.hello.spring.order;

import hello.hello.spring.discount.DiscountPolicy;
import hello.hello.spring.discount.FixDiscountPolicy;
import hello.hello.spring.discount.RateDiscountPolicy;
import hello.hello.spring.member.MemberService;
import hello.hello.spring.member.MemberServiceImpl;
import hello.hello.spring.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(MemberRepository());
    }

    @Bean
    public MemoryMemberRepository MemberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(MemberRepository(), DiscountPolicy());
   }

    @Bean
    public  DiscountPolicy DiscountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
