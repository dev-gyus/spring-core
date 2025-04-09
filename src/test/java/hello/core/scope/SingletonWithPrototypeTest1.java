package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean bean1 = ac.getBean(ClientBean.class);
        int logic1 = bean1.logic();
        ClientBean bean2 = ac.getBean(ClientBean.class);
        int logic2 = bean2.logic();

        System.out.println("logic1 = " + logic1);
        System.out.println("logic2 = " + logic2);

        Assertions.assertThat(logic1).isEqualTo(1);
        Assertions.assertThat(logic2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean;
        private final ObjectProvider<PrototypeBean> prototypeBeanProvider;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean, ObjectProvider<PrototypeBean> protoTypeBeanProvider) {
            this.prototypeBean = prototypeBean;
            this.prototypeBeanProvider = protoTypeBeanProvider;
        }

        public int logic() {
            PrototypeBean newPrototypeBean = prototypeBeanProvider.getObject();
            newPrototypeBean.addCount();
            return newPrototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;
        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
