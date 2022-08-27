package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository()
    //싱글톤 패턴이 깨지는거 아닐까?...
    //SpringContainer은 싱글톤 패턴을 보장해준다.
    //예상
    //System.out.println("call AppConfig.memberService");
    //System.out.println("call AppConfig.memberRepository");
    //System.out.println("call AppConfig.memberRepository");
    //System.out.println("call AppConfig.orderService");
    //System.out.println("call AppConfig.memberRepository");
    //결과
    //System.out.println("call AppConfig.memberService");
    //System.out.println("call AppConfig.memberRepository");
    //System.out.println("call AppConfig.orderService");

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), DiscountPolicy());
   }

    @Bean
    public DiscountPolicy DiscountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
