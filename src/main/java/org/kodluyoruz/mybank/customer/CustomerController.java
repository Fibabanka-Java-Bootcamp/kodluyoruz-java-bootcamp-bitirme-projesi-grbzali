package org.kodluyoruz.mybank.customer;

import org.kodluyoruz.mybank.adress.Adress;
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

    public CustomerController(CustomerServiceImp customerService) {
        this.customerService = customerService;
    }

    @PostMapping(params = {"Name", "Surname", "TC Number","City","Country"})
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@Valid @RequestParam("Name") String name,
                              @RequestParam("Surname") String surname,
                              @RequestParam("TC Number") String tcNumber,
                              @RequestParam("City") String city,
                              @RequestParam("Country") String country) {
        Customer customerData = new Customer();
        customerData.setName(name);
        customerData.setSurname(surname);
        customerData.setTc_number(tcNumber);
        customerData.setCity(city);
        customerData.setCountry(country);
        return customerService.create(customerData).toCustomerDto();
    }

    @GetMapping(params = {"page", "size"})
    public List<CustomerDto> getAll(@Min(value = 0) @RequestParam("page") int page, @RequestParam("size") int size) {
        return customerService.listAll(PageRequest.of(page, size))
                .stream()
                .map(Customer::toCustomerDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable("id") long id) {
        return customerService.get(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id : " + id)).toCustomerDto();
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable("id") long id,@Valid @RequestBody CustomerDto customerDto) {
//        return Objects.requireNonNull(customerServiceImp.update(id, customerDto.toCostumer()).getBody()).toCustomerDto();
        return customerService.update(id,customerDto.toCostumer()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id : " + id)).toCustomerDto();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto deleteCustomer(@PathVariable("id") long id){

        if(customerService.deleteCustomer(id)){
            return customerService.getDeleted(id).get().toCustomerDto();
        }
        return customerService.get(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id : " + id)).toCustomerDto();

    }


}

