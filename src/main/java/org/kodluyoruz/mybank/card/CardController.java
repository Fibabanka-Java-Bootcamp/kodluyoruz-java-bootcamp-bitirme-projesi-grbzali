package org.kodluyoruz.mybank.card;

import org.kodluyoruz.mybank.account.*;

import org.kodluyoruz.mybank.customer.CustomerService;
import org.kodluyoruz.mybank.customer.CustomerServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("api/v1/cards/{customerId}/{accountId}/")
public class CardController {

    private final CardService cardService;
    private final AccountService accountService;
    private final CustomerService customerService;

    public CardController(CardServiceImp cardServiceImp,
                          AccountServiceImp accountServiceImp,
                          CustomerServiceImp customerServiceImp) {
        this.cardService = cardServiceImp;
        this.accountService = accountServiceImp;
        this.customerService = customerServiceImp;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto createCard(@PathVariable("accountId") long accountId,
                              @RequestParam("Card Type") CardType cardType,
                              @Min(0) @RequestParam("Limit") double usableLimit) {


        Optional<Account> accountOptional = accountService.get(accountId);
        accountOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with id : " + accountId)).toAccountDto();

        if (accountOptional.get().getCard() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card already exists for Account Id : " + accountId);


        if ((accountOptional.get().getAccountType() == AccountType.SAVINGS && cardType == CardType.CREDIT) ||
                (accountOptional.get().getAccountType() == AccountType.CHECKING && cardType == CardType.PREPAID)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'" + cardType + " Card' cannot be created for a '" + accountOptional.get().getAccountType() + "' account.");
        }

        Random r = new Random();

        Card card = new Card();
        card.setCardNo(String.valueOf(r.nextInt(1000000000)) + "" + String.valueOf(r.nextInt(10000000)));
        card.setCardType(cardType);
        card.setAccount(accountOptional.get());
        card.setCustomer(accountOptional.get().getCustomer());
        if (cardType == CardType.CREDIT) {
            card.setUsableLimit(usableLimit);
        } else card.setUsableLimit(0);
        return cardService.create(card).toCardDto();
    }

    @PutMapping(value = "/shopping")
    public AccountDto shopping(@RequestParam("Card No") String cardNo, @RequestParam("Amount") long amount) {

        Optional<Card> cardOptional = cardService.getByCardId(cardNo);
        cardOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found with id : " + cardNo)).toCardDto();

        if (cardOptional.get().getCardType() != CardType.CREDIT)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is not a credit card.");

        Optional<Account> accountOptional = accountService.get(cardOptional.get().getAccount().getId());
        accountOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "toAccount not found with id : " + cardOptional.get().getAccount().getId())).toAccountDto();

        return cardService.shopping(accountOptional.get(), cardOptional.get(), amount);

    }

    @GetMapping(value = "/debt")
    public String debtInquiry(@RequestParam("Card No") String cardNo) {

        Optional<Card> cardOptional = cardService.getByCardId(cardNo);
        cardOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found with id : " + cardNo)).toCardDto();

        if (cardOptional.get().getCardType() != CardType.CREDIT)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is not a credit card. No debt found.");

        return "credit card debt: " + String.valueOf(Math.abs(cardOptional.get().getAccount().getBalance()))
                + " " + cardOptional.get().getAccount().getCurrencyCode();
    }

    @PutMapping(value = "/deptPayment")
    public CardDto deptPayment(@RequestParam("Card No") String cardNo, @RequestParam("Amount") long amount) {

        Optional<Card> cardOptional = cardService.getByCardId(cardNo);
        cardOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found with id : " + cardNo)).toCardDto();

        if (cardOptional.get().getCardType() != CardType.CREDIT)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is not a credit card.");

        if(cardOptional.get().getAccount().getBalance() >= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No debt");

        if (cardOptional.get().getAccount().getBalance() + amount > 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Overpayment");

        return cardService.deptPayment(cardOptional.get(), amount);


    }
}