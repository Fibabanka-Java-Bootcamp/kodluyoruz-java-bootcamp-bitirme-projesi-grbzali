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

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@Valid @RequestBody CustomerDto customerDto){
        return customerService.create(customerDto.toCostumer()).toCustomerDto();
    }

    @GetMapping(params = {"page", "size"})
    public List<CustomerDto> list(@Min(value = 0) @RequestParam("page") int page, @RequestParam("size") int size) {
        return customerService.list(PageRequest.of(page, size))
                .stream()
                .map(Customer::toCustomerDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDto getAll(@PathVariable("id") long id){
        return customerService.get(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with id : " + id)).toCustomerDto();
    }
}

