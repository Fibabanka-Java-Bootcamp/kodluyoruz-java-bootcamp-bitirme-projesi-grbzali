package org.kodluyoruz.mybank.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;



public interface CustomerRepository extends CrudRepository<Customer, Long> {


    List<Customer> findAllByName(String name);

    @Query("select c FROM Customer c WHERE c.isDeleted = false")
    Page<Customer> findAll(Pageable page);

    @Transactional
    @Modifying
    @Query("update Customer c set c.isDeleted = true where c.id =?1")
    void delete(long id);

}
