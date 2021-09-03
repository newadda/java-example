package org.onecell.saga.bank.aggregates;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.onecell.saga.bank.commands.CreateAccountCommand;
import org.onecell.saga.bank.commands.DepositCommand;
import org.onecell.saga.bank.events.AccountCreatedEvent;
import org.onecell.saga.bank.events.DepositedEvent;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@NoArgsConstructor
@Aggregate
@Entity
@Slf4j
public class AccountAggregate {
    @Id
    @AggregateIdentifier
    private String accountNumber;

    private BigDecimal balance;

    // 첫 시작은 생성자로..
    @CommandHandler
    public AccountAggregate(CreateAccountCommand command){
        AggregateLifecycle.apply(new AccountCreatedEvent(command.getAccountNumber()));
        log.debug("CreateAccountCommand");
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event){
        this.accountNumber = event.getAccountNumber();
        this.balance = new BigDecimal("0");
    }


    @CommandHandler
    public void on(DepositCommand command){
        AggregateLifecycle.apply(new DepositedEvent(command.getAccountNumber(),command.getAmount()));
        log.debug("DepositCommand");
    }

    @EventSourcingHandler
    protected Object on(DepositedEvent event){
        this.accountNumber = event.getAccountNumber();
        this.balance = this.balance.add( event.getAmount());
        return event;
    }

}
