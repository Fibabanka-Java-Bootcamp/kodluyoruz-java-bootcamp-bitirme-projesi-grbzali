package org.kodluyoruz.mybank.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String surname;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @JsonIgnore
    private boolean isDeleted;

    public CustomerDto toCustomerDto() {
        return CustomerDto.builder()
                .name(this.name)
                .surname(this.surname)
                .build();
    }

//    public String getName(String name) {
//        return name;
//    }
}
