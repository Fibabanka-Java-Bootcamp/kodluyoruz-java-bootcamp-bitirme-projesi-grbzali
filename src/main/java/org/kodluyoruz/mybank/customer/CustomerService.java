package org.kodluyoruz.mybank.customer;

import org.kodluyoruz.mybank.adress.Adress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CustomerService {
    public Optional<Customer> get(long id);
    public Optional<Customer> getDeleted(long id);
    public Customer create(Customer customer);
    public Page<Customer> listAll(PageRequest page);
    public Optional<Customer> update(long id, Customer customer);
    public boolean deleteCustomer(long id);
}
