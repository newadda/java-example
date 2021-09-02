package org.onecell.saga.bank.events;

import lombok.Getter;

@Getter
public class AccountCreatedEvent {
    public final String accountNumber;

    public AccountCreatedEvent(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
