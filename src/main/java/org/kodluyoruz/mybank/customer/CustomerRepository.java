package org.kodluyoruz.mybank.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {


    Optional<Customer> findByIdAndIsDeletedFalse(Long aLong);

    Optional<Customer> findByIdAndIsDeletedTrue(Long aLong);

    @Query("select c FROM Customer c WHERE c.isDeleted = false")
    Page<Customer> findAll(Pageable page);

    @Modifying
    @Query("update Customer c set c.isDeleted = true where c.id =?1")
    void delete(long id);

}
