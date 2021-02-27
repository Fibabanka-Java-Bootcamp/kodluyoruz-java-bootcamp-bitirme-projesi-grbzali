package org.kodluyoruz.mybank.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.kodluyoruz.mybank.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface AccountService {
    public boolean isCustomer(long id);
    public Account create(Account account);
    public Optional<Account> get(long id);
    public Page<Account> listAll(PageRequest page);
    public AccountDto transferAmount(Account toAccount, Account fromAccount, double amount) throws JsonProcessingException;
    public Optional<Account> getByIban(String iban);
}
