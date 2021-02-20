package org.kodluyoruz.mybank.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CustomerService {
    public Optional<Customer> get(long id);
    public Customer create(Customer customer);
    public Page<Customer> list(PageRequest page);
    public ResponseEntity<Customer> update(long id, Customer customer);
    public boolean deleteCustomer(long id);
}
