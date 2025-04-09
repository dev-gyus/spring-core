package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {


    @Test
    void createOrder() {
        // given
        MemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "member1", Grade.BASIC));
        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        // when
        Order order = orderService.createOrder(1L, "itemName1", 10000);
        // then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(0);
    }

}