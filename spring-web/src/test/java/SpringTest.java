import org.junit.Test;
import org.junit.runner.RunWith;
import org.onecellboy.web.Application;
import org.onecellboy.web.mvc.config.PropertyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class }) //시작 클래스
public class SpringTest {

    @Autowired
    PropertyConfig propertyConfig;
    @Test
    public void test1()
    {

    }

}
