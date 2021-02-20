package org.kodluyoruz.mybank.account;

import org.springframework.stereotype.Service;

@Service
public class AccountServiceImp implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceImp(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isCustomer(int id){
        return accountRepository.existsById(id);
    }
    public Account create(Account account){
        return accountRepository.save(account);
    }
}

