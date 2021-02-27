package org.kodluyoruz.mybank.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.kodluyoruz.mybank.card.Card;
import org.kodluyoruz.mybank.customer.Customer;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private long id;
    private CurrencyCode currencyCode;
    private AccountType accountType;
    private String iban;
    private double balance;

    @JsonIgnore
    private Customer customer;

    private Card card;


    public Account toAccount() {
        return Account.builder()
                .id(this.id)
                .currencyCode(this.currencyCode)
                .accountType(this.accountType)
                .customer(this.customer)
                .balance(this.balance)
                .iban(this.iban)
                .card(this.card)
                .build();
    }

}
