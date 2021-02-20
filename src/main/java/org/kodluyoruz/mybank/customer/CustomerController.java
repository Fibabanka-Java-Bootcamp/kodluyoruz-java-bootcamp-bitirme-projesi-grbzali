package org.kodluyoruz.mybank.customer;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerServiceImp customerServiceImp;

    public CustomerController(CustomerServiceImp customerServiceImp) {
        this.customerServiceImp = customerServiceImp;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@Valid @RequestBody CustomerDto customerDto) {
        return customerServiceImp.create(customerDto.toCostumer()).toCustomerDto();
    }

    @GetMapping(params = {"page", "size"})
    public List<CustomerDto> getAll(@Min(value = 0) @RequestParam("page") int page, @RequestParam("size") int size) {
        return customerServiceImp.list(PageRequest.of(page, size))
                .stream()
                .map(Customer::toCustomerDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable("id") long id) {
        return customerServiceImp.get(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id : " + id)).toCustomerDto();
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable("id") long id,@Valid @RequestBody CustomerDto customerDto) {
        return customerServiceImp.update(id, customerDto.toCostumer()).getBody().toCustomerDto();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto deleteCustomer(@PathVariable("id") long id){
        if(customerServiceImp.deleteCustomer(id)){
            return customerServiceImp.get(id).get().toCustomerDto();
        }
        return customerServiceImp.get(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id : " + id)).toCustomerDto();

    }

}

