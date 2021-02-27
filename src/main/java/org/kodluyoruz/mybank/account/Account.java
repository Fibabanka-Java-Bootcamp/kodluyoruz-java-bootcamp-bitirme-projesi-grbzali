package org.kodluyoruz.mybank.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.kodluyoruz.mybank.card.Card;
import org.kodluyoruz.mybank.config.Auditable;

import org.kodluyoruz.mybank.customer.Customer;
import org.kodluyoruz.mybank.transfer.Transfer;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
@Entity
public class Account extends Auditable<String> {

    @Id
    @GeneratedValue
    private long id;

    private String iban;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;


    @Column(columnDefinition="Decimal(10,2) default '0.00'")
    private double balance;

    @JsonIgnore
    @OneToMany(mappedBy = "toAccount")
    private List<Transfer> toTransfer;

    @JsonIgnore
    @OneToMany(mappedBy = "fromAccount")
    private List<Transfer> fromTransfer;

    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToOne(mappedBy = "account",cascade = CascadeType.PERSIST)
    @JoinColumn
    private Card card;

    @JsonIgnore
    private boolean isDeleted;

    public AccountDto toAccountDto() {
        return AccountDto.builder()
                .id(this.id)
                .currencyCode(this.currencyCode)
                .accountType(this.accountType)
                .balance(this.balance)
                .customer(this.customer)
                .iban(this.iban)
                .card(this.card)

                .build();
    }
}
