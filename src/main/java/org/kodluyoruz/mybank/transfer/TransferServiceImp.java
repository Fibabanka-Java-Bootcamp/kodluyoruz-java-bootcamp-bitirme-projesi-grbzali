package org.kodluyoruz.mybank.transfer;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImp implements TransferService {

    private TransferRepository transferRepository;

    public TransferServiceImp(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public Page<Transfer> listAllByAccountId(long accountId, PageRequest page) {
        return transferRepository.findAllByToAccount_IdOrFromAccount_Id(accountId,accountId,page);
    }

}
