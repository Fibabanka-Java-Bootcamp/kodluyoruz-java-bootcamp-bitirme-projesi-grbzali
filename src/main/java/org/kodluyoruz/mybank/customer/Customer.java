package org.kodluyoruz.mybank.customer;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String surname;

    public CustomerDto toCustomerDto() {
        return CustomerDto.builder()
                .name(this.name)
                .surname(this.surname)
                .build();
    }
    
}
