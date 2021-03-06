package org.kodluyoruz.mybank.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;
import lombok.*;
import org.kodluyoruz.mybank.account.AccountDto;
import org.kodluyoruz.mybank.config.Auditable;
import org.kodluyoruz.mybank.account.Account;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transfers")
public class Transfer extends Auditable<String> {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "to_account_id", referencedColumnName = "id")
    @JsonIgnore
    @Nullable
    private Account toAccount;

    @ManyToOne
    @JoinColumn(name = "from_account_id", referencedColumnName = "id")
    @JsonIgnore
    @Nullable
    private Account fromAccount;

    private double amount;

    private double exchangeRate;

    private String currency;

    private String description;

    public TransferDto toTransferDto() {
        return TransferDto.builder()
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
