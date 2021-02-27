package org.kodluyoruz.mybank.card;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CardRepository extends CrudRepository<Card,Long> {
    Optional<Card> findByCardNo(String cardNo);
}
