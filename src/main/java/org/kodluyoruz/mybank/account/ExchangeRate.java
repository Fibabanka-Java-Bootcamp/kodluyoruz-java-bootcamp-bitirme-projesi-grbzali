package org.kodluyoruz.mybank.account;

import lombok.*;
import org.kodluyoruz.mybank.customer.Customer;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExchangeRate implements Serializable {

    private String code;
    private double ex;



}
