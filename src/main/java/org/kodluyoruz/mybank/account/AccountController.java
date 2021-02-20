package org.kodluyoruz.mybank.account;

import org.kodluyoruz.mybank.customer.CustomerServiceImp;
import org.kodluyoruz.mybank.enums.AccountType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/accounts/{customerId}")
public class AccountController {

    private final AccountServiceImp accountServiceImp;
    private final CustomerServiceImp customerServiceImp;

    public AccountController(AccountServiceImp accountServiceImp, CustomerServiceImp customerServiceImp) {
        this.accountServiceImp = accountServiceImp;
        this.customerServiceImp = customerServiceImp;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createSaving(@RequestParam("customerId") long customerId, @RequestBody AccountDto accountDto)  {
        return customerServiceImp.get(customerId).map(customer -> {
            accountDto.setCustomer(customer);
            return accountServiceImp.create(accountDto.toAccount()).toAccountDto();
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id : " + customerId));
    }
}
