package org.kodluyoruz.mybank.account;

import org.kodluyoruz.mybank.customer.Customer;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private long id;

    private String iban;

    private String currency_code;
    //TODO: Kartid relations
    //private Card card;
    private double usableLimit;
    private boolean activeStatus;
    private String BranchCode;
    //private dateTime CreatedDate;
    //private enum accountType; // Vadesiz Mevduat hesabı, Birikim Hesabı
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


}
