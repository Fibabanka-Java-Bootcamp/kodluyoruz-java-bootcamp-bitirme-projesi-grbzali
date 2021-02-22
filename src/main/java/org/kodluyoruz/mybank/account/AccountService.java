package org.kodluyoruz.mybank.account;

import java.util.Optional;

public interface AccountService {
    public boolean isCustomer(long id);
    public Account create(Account account);
    public Optional<Account> get(long id);
    public AccountDto transferAmount(Account toAccount, Account fromAccount, double amount);
}
