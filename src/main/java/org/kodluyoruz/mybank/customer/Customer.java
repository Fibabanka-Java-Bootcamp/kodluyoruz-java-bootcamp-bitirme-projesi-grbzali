package org.kodluyoruz.mybank.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.kodluyoruz.mybank.adress.Adress;
import org.kodluyoruz.mybank.card.Card;
import org.kodluyoruz.mybank.config.Auditable;
import org.kodluyoruz.mybank.account.Account;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends Auditable<String> {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String surname;

    @Column(length = 11)
    private String tc_number;

    private String city;

    private String country;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Account> accounts;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Card> cards;

    @JsonIgnore
    private boolean isDeleted;

    public CustomerDto toCustomerDto() {
        return CustomerDto.builder()
                .id(this.id)
                .name(this.name)
                .tc_number(this.tc_number)
                .surname(this.surname)
                .city(this.city)
                .country(this.country)
                .accounts(this.accounts)
                .cards(this.cards)

                .build();
    }

}
