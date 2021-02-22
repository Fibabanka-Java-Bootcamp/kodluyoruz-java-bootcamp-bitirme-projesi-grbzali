package org.kodluyoruz.mybank.transfer;

import org.kodluyoruz.mybank.account.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue
    private long id;

    private double amount;

    private String description;

    private LocalDateTime date;

    private Account toAccount;

    private Account fromAccount;

}
