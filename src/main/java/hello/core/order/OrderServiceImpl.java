package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor//final필드를 기준으로 생성자를 만들어줌
public class OrderServiceImpl implements OrderService {
    //생성자 주입을 선택하면 반드시 final을 써줘야 한다
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /* 생성자 주입 */

//    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        //생성자 주입이 수정자 주입보다 먼저 일어남
//        System.out.println("1. memberRepository = " + memberRepository);
//        System.out.println("1. discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

    /* 필드 주입 */
    /*
    @Autowired  private MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    @Autowired  private DiscountPolicy discountPolicy;

    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
    */

    /* 수정자 주입 */
    /*
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    //@Autowried(required = false) 선택 가능
    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("2. memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        //System.out.println("2. discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }
    */
}
