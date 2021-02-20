package org.kodluyoruz.mybank.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.kodluyoruz.mybank.customer.Customer;
import org.kodluyoruz.mybank.enums.AccountType;
import org.kodluyoruz.mybank.enums.CurrencyCode;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private CurrencyCode currencyCode;
    private AccountType accountType;
    private double usableLimit;

    @JsonIgnore
    private Customer customer;

    public Account toAccount() {
        return Account.builder()
                .currencyCode(this.currencyCode)
                .accountType(this.accountType)
                .usableLimit(this.usableLimit)
                .customer(this.customer)

                .build();
    }

}
