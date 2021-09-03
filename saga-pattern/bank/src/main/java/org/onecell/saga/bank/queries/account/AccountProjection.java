package org.onecell.saga.bank.queries.account;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.onecell.saga.bank.events.AccountCreatedEvent;

import java.time.Instant;

public class AccountProjection {
    @EventHandler
    public void on(final AccountCreatedEvent event, @Timestamp Instant createdAt) {
        //repository.save(new RoomQueryModel().setRoomId(event.getRoomId())
       //         .setCreatedAt(createdAt));
    }

}
