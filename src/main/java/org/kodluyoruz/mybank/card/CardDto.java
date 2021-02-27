package org.kodluyoruz.mybank.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.kodluyoruz.mybank.account.Account;
import org.kodluyoruz.mybank.customer.Customer;


import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {

    private long id;
    private String cardNo;
    private CardType cardType;
    @JsonIgnore
    private Account account;
    @JsonIgnore
    private Customer customer;
    private double usableLimit;

    public Card toCard() {
        return Card.builder()
                .id(this.id)
                .cardNo(this.cardNo)
                .account(this.account)
                .cardType(this.cardType)
                .customer(this.customer)
                .usableLimit(this.usableLimit)

                .build();
    }
}
