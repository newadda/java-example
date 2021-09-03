package org.onecell.saga.bank.queries.account;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.axonframework.modelling.command.GenericJpaRepository;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AccountModel implements Serializable {
    private static final long serialVersionUID = -6008284836583310598L;

    @Id
    private String accountNumber;

    private BigDecimal balance;

    Instant createdAt;
}
