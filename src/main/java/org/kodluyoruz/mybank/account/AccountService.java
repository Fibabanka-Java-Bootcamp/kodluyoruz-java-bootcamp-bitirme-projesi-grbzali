package org.kodluyoruz.mybank.account;

public interface AccountService {
    public boolean isCustomer(int id);
    public Account create(Account account);
}
