package org.kodluyoruz.mybank.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.kodluyoruz.mybank.customer.CustomerService;
import org.kodluyoruz.mybank.customer.CustomerServiceImp;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/accounts/{customerId}")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    public AccountController(AccountServiceImp accountService, CustomerServiceImp customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @PostMapping(params = {"accountType", "currency"})
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto create(@PathVariable("customerId") long customerId,
                             @RequestParam("accountType") AccountType accountType,
                             @RequestParam("currency") CurrencyCode currency) {
        Account account = new Account();
        Random r = new Random();

        return customerService.get(customerId).map(customer -> {
            account.setCustomer(customer);
            account.setAccountType(accountType);
            account.setCurrencyCode(currency);

            account.setIban("TR" + String.valueOf(r.nextInt(100))
                    + "001030" + String.valueOf(r.nextInt(1000000000))
                    +String.valueOf(r.nextInt(10000000)));

            return accountService.create(account).toAccountDto();
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id : " + customerId));
    }

    @PutMapping(value = "/transfer", params = {"toAccountIban", "fromAccountIban", "amount"})
    public AccountDto transferAmount(@RequestParam("toAccountIban") String toAccountIban,
                                         @RequestParam("fromAccountIban") String fromAccountIban,
                                         @RequestParam long amount) throws JsonProcessingException {



        Optional<Account> toAccount = accountService.getByIban(toAccountIban);
        toAccount.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "toAccount not found with Iban : " + toAccountIban)).toAccountDto();
        Optional<Account> fromAccount = accountService.getByIban(fromAccountIban);
        fromAccount.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "fromAccount not found with Iban : " + fromAccountIban)).toAccountDto();

        if(fromAccount.get().getAccountType() == AccountType.SAVINGS)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Money transfer is not possible with SAVINGS account.");


        return accountService.transferAmount(toAccount.get(),fromAccount.get(),amount);

    }

    @GetMapping(params = {"page", "size"})
    public List<AccountDto> getAll(@Min(value = 0) @RequestParam("page") int page,@Min(0) @RequestParam("size") int size) {
        return accountService.listAll(PageRequest.of(page, size))
                .stream()
                .map(Account::toAccountDto)
                .collect(Collectors.toList());
    }


}
