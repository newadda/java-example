package org.onecell.saga.bank.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    public final String accountNumber;

    public CreateAccountCommand(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
