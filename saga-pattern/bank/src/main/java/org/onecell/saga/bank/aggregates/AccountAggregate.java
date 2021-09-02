package org.onecell.saga.bank.aggregates;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.onecell.saga.bank.commands.CreateAccountCommand;
import org.onecell.saga.bank.events.AccountCreatedEvent;

import java.math.BigDecimal;

@NoArgsConstructor
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountNumber;

    private BigDecimal balance;

    // 첫 시작은 생성자로..
    @CommandHandler
    public AccountAggregate(CreateAccountCommand command){
        AggregateLifecycle.apply(new AccountCreatedEvent(command.getAccountNumber()));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event){
        this.accountNumber = event.getAccountNumber();
        this.balance = new BigDecimal("0");
    }

}
