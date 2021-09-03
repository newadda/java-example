package org.onecell.saga.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DepositReq {
    String accountNumber;
    BigDecimal amount;
}
