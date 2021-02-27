package org.kodluyoruz.mybank.customer;

import lombok.*;
import org.kodluyoruz.mybank.account.Account;
import org.kodluyoruz.mybank.adress.Adress;
import org.kodluyoruz.mybank.card.Card;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {

    private long id;
    @NotBlank(message = "Name for the user is mandatory")
    private String name;
    @NotBlank(message = "Surname for the user is mandatory")
    private String surname;
    @NotBlank(message = "tc number for the user is mandatory")
    private String tc_number;

    private String city;

    private String country;

    private List<Account> accounts;

    private List<Card> cards;



    public Customer toCostumer() {
        return Customer.builder()
                .id(this.id)
                .name(this.name)
                .surname(this.surname)
                .tc_number(this.tc_number)
                .city(this.city)
                .country(this.country)
                .accounts(this.accounts)
                .cards(this.cards)
                .build();
    }
}
