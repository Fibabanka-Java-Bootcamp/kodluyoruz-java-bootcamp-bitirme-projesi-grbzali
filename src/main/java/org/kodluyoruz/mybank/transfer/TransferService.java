package org.kodluyoruz.mybank.transfer;

import org.kodluyoruz.mybank.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TransferService {
    public Page<Transfer> listAllByAccountId(long accountId,PageRequest page);
}
