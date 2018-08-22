import org.junit.Test;
import org.onecellboy.Basic;
import org.onecellboy.annotation.BasicConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import java.util.Objects;


public class AnnotationTest {

    @Test
    public void test()
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class);

        Basic bean = context.getBean(Basic.class);

        System.out.println(bean.getStr());

        Basic bean1 = context.getBean("basic3",Basic.class);
        Basic bean2 = context.getBean("basic3",Basic.class);

        System.out.println(Objects.hashCode(bean1));
        System.out.println(Objects.hashCode(bean2));



    }





}
