import org.junit.Test;
import org.onecellboy.Basic;
import org.onecellboy.IBasic;
import org.onecellboy.annotation.BasicConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;


public class AnnotationTest {

   static class Handler implements InvocationHandler {
        private final Object original;

        public Handler(Object original) {
            this.original = original;
        }

       @Override
       public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           System.out.println("BEFORE");
           Object invoke = method.invoke(original, args);
           System.out.println("AFTER");
           return invoke;
       }
   }

    @Test
    public void test()
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class);


        Basic bean = context.getBean(Basic.class);

        System.out.println(bean.getStr());

        Basic bean1 = context.getBean("basic",Basic.class);
        System.out.println(Objects.hashCode(bean1));

        //((AnnotationConfigApplicationContext) context).getBeanFactory().registerSingleton("str",new String("azzzzz"));

        Basic bean2 = context.getBean("basic",Basic.class);




        System.out.println(Objects.hashCode(bean2));


        Basic test =new Basic("test");
        Handler handler = new Handler(test);

        IBasic o = (IBasic)Proxy.newProxyInstance(Basic.class.getClassLoader(), new Class[]{IBasic.class}, handler);

        System.out.println(o.getStr());

    }





}
