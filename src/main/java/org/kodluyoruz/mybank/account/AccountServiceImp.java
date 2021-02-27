package org.kodluyoruz.mybank.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.kodluyoruz.mybank.templateEntity.Exchange;
import org.kodluyoruz.mybank.templateEntity.Rates;
import org.kodluyoruz.mybank.transfer.Transfer;
import org.kodluyoruz.mybank.transfer.TransferRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class AccountServiceImp implements AccountService {

    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;
    private final TransferRepository transferRepository;

    public AccountServiceImp(AccountRepository accountRepository,
                             RestTemplateBuilder restTemplateBuilder,
                             TransferRepository transferRepository) {

        this.restTemplate = restTemplateBuilder.rootUri("https://api.exchangeratesapi.io").build();
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    public boolean isCustomer(long id) {
        return accountRepository.existsById(id);
    }

    public Account create(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> get(long id) {
        return accountRepository.findById(id);
    }

    public Page<Account> listAll(PageRequest page){
        return accountRepository.findAll(page);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountDto transferAmount(Account toAccount, Account fromAccount, double amount) throws JsonProcessingException {


        if (fromAccount.getBalance() < amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"insufficient balance");
        }

        if (fromAccount.getCurrencyCode() == toAccount.getCurrencyCode()) {
            accountRepository.toBalanceIncById(toAccount.getId(), amount);
            accountRepository.fromBalanceDecById(fromAccount.getId(), amount);

            Transfer transfer = new Transfer();
            transfer.setFromAccount(fromAccount);
            transfer.setToAccount(toAccount);
            transfer.setAmount(amount);
            transfer.setCurrency(String.valueOf(fromAccount.getCurrencyCode()));
            transfer.setExchangeRate(1);
            transferRepository.save(transfer);

            return accountRepository.findById(fromAccount.getId()).get().toAccountDto();
        }
        Map<String,String> parameters = new HashMap<>();
        parameters.put("base", "TRY");
        Exchange exchange = restTemplate.getForObject("/latest?base={base}",Exchange.class,parameters);

        System.out.println(exchange.getBase());

        System.out.println(exchange.getRates().get("EUR"));

//        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(rates1));


        String str = String.valueOf(toAccount.getCurrencyCode());

        double result = (Double) exchange.getRates().get(str);
        accountRepository.toBalanceIncById(toAccount.getId(), amount * result);
        accountRepository.fromBalanceDecById(fromAccount.getId(), amount);
        Transfer transfer = new Transfer();
        transfer.setFromAccount(fromAccount);
        transfer.setToAccount(toAccount);
        transfer.setAmount(amount);
        transfer.setCurrency(String.valueOf(fromAccount.getCurrencyCode()));
        transfer.setExchangeRate(result);
        transfer.setDescription("transfer to:" + toAccount.getCurrencyCode());
        transferRepository.save(transfer);

        return accountRepository.findById(fromAccount.getId()).get().toAccountDto();

    }

    @Override
    public Optional<Account> getByIban(String iban) {
        return accountRepository.findByIban(iban);
    }

}

