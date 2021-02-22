package org.kodluyoruz.mybank.account;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AccountServiceImp implements AccountService{

    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;

    public AccountServiceImp(AccountRepository accountRepository, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri("https://api.exchangeratesapi.io/").build();
        this.accountRepository = accountRepository;
    }

    public boolean isCustomer(long id){
        return accountRepository.existsById(id);
    }
    public Account create(Account account){
        return accountRepository.save(account);
    }

    public Optional<Account> get(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public AccountDto transferAmount(Account toAccount, Account fromAccount, double amount) {

        if(fromAccount.getAccountType() == toAccount.getAccountType()){
            accountRepository.withdrawAmountById(fromAccount.getId(), amount);
            accountRepository.saveBalanceById(toAccount.getId(),amount);
            return accountRepository.findById(fromAccount.getId()).get().toAccountDto();
            //yetersiz bakiye kontrolü ekle
        }
        Map<String,String> parameters = new HashMap<>();
        parameters.put("base","TRY");
        ExchangeRate[] exchangeRates = restTemplate.getForObject("/latest?base=TRY",ExchangeRate[].class,parameters);

        List<ExchangeRate> rates = Arrays.asList(exchangeRates);

        return null;

    }


}

