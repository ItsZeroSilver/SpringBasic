package hello.hello.spring.discount;

import hello.hello.spring.member.Grade;
import hello.hello.spring.member.Member;

public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPrice = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP){
            return price * discountPrice/100;
        } else {
            return 0;
        }
    }
}
