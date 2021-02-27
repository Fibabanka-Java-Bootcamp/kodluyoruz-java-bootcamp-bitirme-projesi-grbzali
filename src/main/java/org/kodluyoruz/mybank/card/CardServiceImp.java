package org.kodluyoruz.mybank.card;

import org.kodluyoruz.mybank.account.Account;
import org.kodluyoruz.mybank.account.AccountDto;
import org.kodluyoruz.mybank.account.AccountRepository;
import org.kodluyoruz.mybank.customer.CustomerRepository;
import org.kodluyoruz.mybank.transfer.Transfer;
import org.kodluyoruz.mybank.transfer.TransferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CardServiceImp implements CardService{

    private final CardRepository cardRepository;
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    public CardServiceImp(CardRepository cardRepository, TransferRepository transferRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Card create(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Optional<Card> getByCardId(String cardId) {
        return cardRepository.findByCardNo(cardId);
    }

    @Transactional
    @Override
    public AccountDto shopping(Account account, Card card, double amount) {
        if (card.getUsableLimit() < amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient credit card limit.");
        }
            Transfer shopping = new Transfer();
            shopping.setFromAccount(account);
            shopping.setToAccount(null);
            shopping.setAmount(amount);
            shopping.setCurrency(String.valueOf(account.getCurrencyCode()));
            shopping.setExchangeRate(1);
            shopping.setDescription("shopping");
            transferRepository.save(shopping);

            card.setUsableLimit(card.getUsableLimit()-amount);
            account.setBalance(account.getBalance()-amount);

            return accountRepository.findById(account.getId()).get().toAccountDto();
        }

    @Override
    public CardDto deptPayment(Card card, double amount) {
        card.setUsableLimit(card.getUsableLimit()+amount);
        card.getAccount().setBalance(card.getAccount().getBalance()+amount);

        Transfer payment = new Transfer();
        payment.setFromAccount(null);
        payment.setToAccount(card.getAccount());
        payment.setAmount(amount);
        payment.setCurrency(String.valueOf(card.getAccount().getCurrencyCode()));
        payment.setExchangeRate(1);
        payment.setDescription("payment");

        transferRepository.save(payment);

        return cardRepository.save(card).toCardDto();

    }


}
