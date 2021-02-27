package org.kodluyoruz.mybank.adress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.kodluyoruz.mybank.customer.Customer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AdressDto {
    @JsonIgnore
    private long id;

    private String city;
    private String country;
    private String postCode;
    @JsonIgnore
    private Customer customer;

    public Adress toAdress() {
        return Adress.builder()
                .id(this.id)
                .city(this.city)
                .country(this.country)
                .postCode(this.postCode)
                .customer(this.customer)
                .build();
    }
}
