package org.kodluyoruz.mybank.adress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.kodluyoruz.mybank.customer.Customer;
import org.kodluyoruz.mybank.customer.CustomerDto;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "adresses")
public class Adress {

    @JsonIgnore
    @Id
    @GeneratedValue
    private long id;

    private String city;
    private String country;
    private String postCode;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public AdressDto toAdressDto() {
        return AdressDto.builder()
                .id(this.id)
                .city(this.city)
                .country(this.country)
                .postCode(this.postCode)
                .customer(this.customer)
                .build();
    }
}
