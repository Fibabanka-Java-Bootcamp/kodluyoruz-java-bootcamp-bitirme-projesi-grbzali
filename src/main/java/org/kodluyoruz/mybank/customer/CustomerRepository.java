package org.kodluyoruz.mybank.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAllByName(String name);
    Page<Customer> findAll(Pageable page);
}
