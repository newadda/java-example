package org.onecell.saga.bank.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.onecell.saga.bank.commands.CreateAccountCommand;
import org.onecell.saga.bank.commands.DepositCommand;
import org.onecell.saga.bank.dto.DepositReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private CommandGateway commandGateway;

    /// 계좌 개설
    public void createAccount()
    {
        Object o = commandGateway.sendAndWait(new CreateAccountCommand("123-45-6789"));
        System.out.println();

    }

    // 입금
    public void deposit(DepositReq req)
    {
        Object o = commandGateway.sendAndWait(new DepositCommand("123-45-6789", req.getAmount()));
        System.out.println();
    }

}
