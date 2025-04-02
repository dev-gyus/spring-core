package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    // 싱글톤 쓸때는 절대로 클라이언트들의 요청에 따라 값이 변하는 공유 필드를 두지말 것
    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // Thread A: A 사용자 10000원 주문
//        statefulService1.order("userA", 10000);
        int userAprice = statefulService1.order("userA", 10000);
        // Thread B: B 사용자 20000원 주문
//        statefulService1.order("userB", 20000);
        int userBprice = statefulService2.order("userB", 20000);

        // Thread A: 사용자 A 주문 금액 조회
//        int price1 = statefulService1.getPrice();

//        System.out.println("price = " + price1);
        System.out.println("userAprice = " + userAprice);
        System.out.println("userBprice = " + userBprice);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}