package org.kodluyoruz.mybank.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.kodluyoruz.mybank.account.Account;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDto {

    private long id;

    @JsonIgnore
    private Account toAccount;

    @JsonIgnore
    private Account fromAccount;

    private double amount;

    private double exchangeRate;

    private String currency;

    private String description;

    public Transfer toTransfer() {
        return Transfer.builder()
                .id(this.id)
                .toAccount(this.toAccount)
                .fromAccount(this.fromAccount)
                .amount(this.amount)
                .exchangeRate(this.exchangeRate)
                .currency(this.currency)
                .description(this.description)
                .build();
    }
}
