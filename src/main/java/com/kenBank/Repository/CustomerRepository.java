package com.kenBank.Repository;

import com.kenBank.pojo.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByEmail(String email);
    // here either we can get the row in a list or like this
}
