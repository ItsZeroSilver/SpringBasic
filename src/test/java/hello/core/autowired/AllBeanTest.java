package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {
    @Test
    void findAllBean(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member1 = new Member(1L, "userA", Grade.VIP);
        int priceA = discountService.discount(member1, 1000, "fixDiscountPolicy");
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(priceA).isEqualTo(1000);
        Member member2 = new Member(2L, "userB", Grade.VIP);
        int priceB = discountService.discount(member2, 20000, "rateDiscountPolicy");
        assertThat(priceB).isEqualTo(2000);
    }

    static class DiscountService{
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies){
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountPrice){
            DiscountPolicy discountPolicy = policyMap.get(discountPrice);
            return discountPolicy.discount(member, price);
        }

    }
}
