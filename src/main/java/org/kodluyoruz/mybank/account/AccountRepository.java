package org.kodluyoruz.mybank.account;

import org.kodluyoruz.mybank.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("select a FROM Account a WHERE a.isDeleted = false")
    Page<Account> findAll(Pageable page);

    @Modifying(clearAutomatically = true)
    @Query("update Account set balance = balance+?2 where id=?1")
    void toBalanceIncById(long id, double balance);

    @Modifying(clearAutomatically = true)
    @Query("update Account set balance = balance-?2 where id=?1")
    void fromBalanceDecById(long id, double balance);

    Optional<Account> findByIban(String iban);
}
