package org.kodluyoruz.mybank.account;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Account set balance = balance+?2 where id=?1")
    void saveBalanceById(long id, double balance);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Account set balance = balance-?2 where id=?1")
    void withdrawAmountById(long id, double balance);

}
