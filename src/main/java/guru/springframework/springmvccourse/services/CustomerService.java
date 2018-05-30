package guru.springframework.springmvccourse.services;

import guru.springframework.springmvccourse.domain.Customer;

import java.util.List;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
public interface CustomerService {
    List<Customer> getAll();
    Customer get(Integer id);
    Customer saveOrUpdate(Customer customer);
    void delete(Integer id);
}
