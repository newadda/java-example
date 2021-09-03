package org.onecell.saga.bank.events;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class DepositedEvent {
    public final String accountNumber;

    public final BigDecimal amount;

    public DepositedEvent(String accountNumber, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
}
