package org.onecell.spring.jta.service;

import org.onecell.spring.jta.entity.Test01;
import org.onecell.spring.jta.lib.DBKeyContext;
import org.onecell.spring.jta.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;

    @Autowired
    DBKeyContext<String> dbKeyContext;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Transactional("xaTxManager")
    public List<Test01>  test01()
    {
        List<Test01> total = new LinkedList<>();

        dbKeyContext.setDbKey("1");
        List<Test01> list1 = testRepository.list();

        dbKeyContext.setDbKey("2");
        List<Test01> list2 = testRepository.list();

        total.addAll(list1);
        total.addAll(list2);

        return total;
    }


    @Transactional("xaTxManager")
    public void  test02()
    {

/*
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionTemplate template = new TransactionTemplate(transactionManager, definition);

        template.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                dbKeyContext.setDbKey("1");
                Test01 t1 = new Test01();
                t1.setUsername("테스트1");
                testRepository.save(t1);
                testRepository.getEntityManager().flush();

                dbKeyContext.setDbKey("2");
                Test01 t2 = new Test01();
                t2.setUsername("테스트2");
                testRepository.save(t2);
                testRepository.getEntityManager().flush();
                return null;
            }
        });

*/
        dbKeyContext.setDbKey("1");
        Test01 t1 = new Test01();
        t1.setUsername("테스트1");
        testRepository.save(t1);
        EntityManager entityManager2 = testRepository.getEntityManager();

        dbKeyContext.setDbKey("1");
        EntityManager entityManager = testRepository.getEntityManager();
        Test01 t3 = new Test01();
        t3.setUsername("테스트1");
        testRepository.save(t3);


        dbKeyContext.setDbKey("2");
        Test01 t2 = new Test01();
        t2.setUsername("테스트2");
        testRepository.save(t2);
        EntityManager entityManager1 = testRepository.getEntityManager();




    }



    /// 일부러 오류나게
    @Transactional("xaTxManager")
    public void  test03()
    {



        dbKeyContext.setDbKey("1");
        Test01 t1 = new Test01();
        t1.setUsername("테스트1");
        testRepository.save(t1);
        testRepository.getEntityManager().flush();

        if(true)
        {
            throw new RuntimeException("test");
        }

        dbKeyContext.setDbKey("2");
        Test01 t2 = new Test01();
        t2.setUsername("테스트2");
        testRepository.save(t2);
        testRepository.getEntityManager().flush();

    }
}
