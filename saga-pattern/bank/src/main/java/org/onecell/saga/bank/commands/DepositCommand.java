package org.onecell.saga.bank.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Getter
public class DepositCommand {

    @TargetAggregateIdentifier
    public final String accountNumber;

    public final BigDecimal amount;

    public DepositCommand(String accountNumber, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
}
