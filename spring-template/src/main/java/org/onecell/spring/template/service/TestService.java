package org.onecell.spring.template.service;


import org.onecell.spring.template.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;



    @Transactional(transactionManager = "TransactionManager")
    public void findAll()
    {

    }

}
