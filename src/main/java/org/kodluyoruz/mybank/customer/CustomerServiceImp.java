package org.kodluyoruz.mybank.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImp(CustomerRepository customerRepository) {
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

    public ResponseEntity<Customer> update(long id, Customer customer){
        Optional<Customer> customerData = customerRepository.findById(id);

        if (customerData.isPresent()) {
            Customer _customer = customerData.get();
            _customer.setName(customer.getName());
            _customer.setSurname(customer.getSurname());
            return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
        } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteCustomer(long id){
        Optional<Customer> customerData = customerRepository.findById(id);
        if (customerData.isPresent()) {
            customerRepository.delete(id);
            return true;
        }
        return false;
    }
}
