package org.kodluyoruz.mybank.card;


import org.kodluyoruz.mybank.account.Account;
import org.kodluyoruz.mybank.account.AccountDto;

import java.util.Optional;

public interface CardService {
    public Card create(Card customer);

    public Optional<Card> getByCardId(String cardId);

    public AccountDto shopping(Account account, Card card, double amount);

    public CardDto deptPayment(Card card, double amount);
}
