package org.kodluyoruz.mybank.transfer;

import org.kodluyoruz.mybank.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransferRepository extends CrudRepository<Transfer, Long> {

    Page<Transfer> findAllByToAccount_IdOrFromAccount_Id(Long toAccountId, Long fromAccountId, Pageable page);

}
