package hello.hello.spring.Order;

import hello.hello.spring.order.AppConfig;
import hello.hello.spring.order.Order;
import hello.hello.spring.order.OrderService;
import hello.hello.spring.order.OrderServiceImpl;
import hello.hello.spring.member.Grade;
import hello.hello.spring.member.Member;
import hello.hello.spring.member.MemberService;
import hello.hello.spring.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    MemberService memberService;
    OrderService orderService;
    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder(){
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }
}
