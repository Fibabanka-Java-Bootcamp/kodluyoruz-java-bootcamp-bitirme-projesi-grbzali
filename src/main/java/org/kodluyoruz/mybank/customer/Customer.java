package org.kodluyoruz.mybank.customer;

import lombok.*;
import org.kodluyoruz.mybank.account.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    public CustomerDto toCustomerDto() {
        return CustomerDto.builder()
                .name(this.name)
                .surname(this.surname)
                .build();
    }
    
}
