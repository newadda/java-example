package org.onecell.saga.bank.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private CommandGateway commandGateway;

    /// 계좌 개설
    public void createAccount()
    {
       // commandGateway.sendAndWait()

    }

}
