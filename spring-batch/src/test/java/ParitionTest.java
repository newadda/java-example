import com.google.common.base.Objects;
import org.junit.Test;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@EnableBatchProcessing
public class ParitionTest {

    public class TestClass{

    }

    public TestClass getTestClass()
    {
        return new TestClass();
    }


    @Test
    public void test()
    {
        ApplicationContext context = new GenericXmlApplicationContext("classpath:bean.xml");
        TestClass messageService = context.getBean("messageService", TestClass.class);




        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String name:beanDefinitionNames
                ) {
            System.out.println(name);
        }


        int hash = java.util.Objects.hash(messageService);
        System.out.println(hash);
        System.out.println(java.util.Objects.hash(getTestClass()));

    }



}
