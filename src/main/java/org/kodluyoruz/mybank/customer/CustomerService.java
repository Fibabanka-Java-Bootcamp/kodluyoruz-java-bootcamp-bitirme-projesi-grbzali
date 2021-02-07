package org.kodluyoruz.mybank.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> get(long id){
        return customerRepository.findById(id);
    }
    public Customer create(Customer customer){
        return customerRepository.save(customer);
    }

    public Page<Customer> list(PageRequest page){
        return customerRepository.findAll(page);
    }

}
