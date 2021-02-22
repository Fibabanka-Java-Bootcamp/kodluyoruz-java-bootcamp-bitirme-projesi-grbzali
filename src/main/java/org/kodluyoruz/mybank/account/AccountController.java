package org.kodluyoruz.mybank.account;

import org.kodluyoruz.mybank.customer.CustomerServiceImp;
import org.kodluyoruz.mybank.enums.AccountType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
    public AccountDto createSaving(@PathVariable("customerId") long customerId, @RequestBody AccountDto accountDto)  {
        return customerServiceImp.get(customerId).map(customer -> {
            accountDto.setCustomer(customer);
            return accountServiceImp.create(accountDto.toAccount()).toAccountDto();
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id : " + customerId));
    }
    @PutMapping(value = "/transfer", params = {"toAccountId", "fromAccountId", "amount"})
    public AccountDto transferAmount(@RequestParam("toAccountId") long toAccountId,
                                         @RequestParam("fromAccountId") long fromAccountId,
                                         long amount){

        Optional<Account> toAccount = accountServiceImp.get(toAccountId);
        toAccount.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "toAccount not found with id : " + toAccountId)).toAccountDto();
        Optional<Account> fromAccount = accountServiceImp.get(fromAccountId);
        fromAccount.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "fromAccount not found with id : " + fromAccountId)).toAccountDto();

        if(fromAccount.get().getAccountType().equals(AccountType.SAVINGS)){
            return null; //type exception
        }

        return accountServiceImp.transferAmount(toAccount.get(),fromAccount.get(),amount);

    }
}
