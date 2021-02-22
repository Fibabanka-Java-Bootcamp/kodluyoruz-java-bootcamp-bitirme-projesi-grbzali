package org.kodluyoruz.mybank.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.kodluyoruz.mybank.customer.Customer;
import org.kodluyoruz.mybank.enums.AccountType;
import org.kodluyoruz.mybank.enums.CurrencyCode;


import javax.persistence.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
@Entity
public class Account {

    @Id
    @GeneratedValue
    private long id;

    private int iban;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;

    private double usableLimit;
    @Column(columnDefinition="Decimal(10,2) default '0.00'")
    private double balance;

    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @JsonIgnore
    private boolean isDeleted;

    private LocalDateTime createdAt;


    //TODO: Kartid relation
    //private Card card;

    public AccountDto toAccountDto() {
        return AccountDto.builder()
                .currencyCode(this.currencyCode)
                .usableLimit(this.usableLimit)
                .accountType(this.accountType)
                .build();
    }
}
