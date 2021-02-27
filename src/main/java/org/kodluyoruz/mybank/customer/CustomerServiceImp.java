package org.kodluyoruz.mybank.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImp(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> get(long id){
        return customerRepository.findByIdAndIsDeletedFalse(id);
    }

    public Optional<Customer> getDeleted(long id){
        return customerRepository.findByIdAndIsDeletedTrue(id);
    }


    public Customer create(Customer customer){

        return customerRepository.save(customer);
    }

    public Page<Customer> listAll(PageRequest page){
        return customerRepository.findAll(page);
    }

    public Optional<Customer> update(long id, Customer customer) {
        Optional<Customer> customerData = customerRepository.findByIdAndIsDeletedFalse(id);

        if (customerData.isPresent()) {
            Customer _customer = customerData.get();
            _customer.setName(customer.getName());
            _customer.setSurname(customer.getSurname());
            _customer.setTc_number(customer.getTc_number());
            _customer.setCountry(customer.getCountry());
            _customer.setCity(customer.getCity());
            return Optional.of(customerRepository.save(_customer));
//            return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
//        } else {
//                return new ResponseEntity<>(customerData.get(), HttpStatus.NOT_FOUND);
//        }
        }
        return customerData;
    }
    @Transactional
    public boolean deleteCustomer(long id){
        Optional<Customer> customerData = customerRepository.findByIdAndIsDeletedFalse(id);
        System.out.println(customerData.get());
        if (customerData.isPresent()) {
            customerRepository.delete(id);
            return true;
        }
        return false;
    }
}
