package org.kodluyoruz.mybank.customer;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {

    @NotBlank(message = "Name for the user is mandatory")
    private String name;
    @NotBlank(message = "Surname for the user is mandatory")
    private String surname;

    public Customer toCostumer() {
        return Customer.builder()
                .name(this.name)
                .surname(this.surname)
                .build();
    }
}
