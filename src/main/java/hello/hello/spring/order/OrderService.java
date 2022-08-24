package hello.hello.spring.order;


public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
