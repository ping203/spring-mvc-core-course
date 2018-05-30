package guru.springframework.springmvccourse.services;

import guru.springframework.springmvccourse.domain.Customer;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<Integer, Customer> customerservice;

    public CustomerServiceImpl() {
        loadCustomers();
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(customerservice.values());
    }

    @Override
    public Customer get(Integer id) {
        Objects.requireNonNull(customerservice.get(id), "Customer with specified id doesn't exist");
        return customerservice.get(id);
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        Objects.requireNonNull(customer, "Customer can't be null");
        if (customer.isNew()) {
            customer.setId(getNextKey());
        }
        customerservice.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public void delete(Integer id) {
        Objects.requireNonNull(customerservice.get(id), "Customer with specified id doesn't exist");
        customerservice.remove(id);
    }

    private Integer getNextKey(){
        return Collections.max(customerservice.keySet()) + 1;
    }

    private void loadCustomers() {
        final AtomicInteger counter = new AtomicInteger();
        customerservice = Stream.generate(Customer::new).limit(5).peek(customer -> {
            customer.setId(counter.incrementAndGet());
            customer.setFirstName(RandomStringUtils.randomAlphabetic(3, 7));
            customer.setLastName(RandomStringUtils.randomAlphabetic(5, 9));
            customer.setEmail(RandomStringUtils.randomAlphabetic(8, 15));
            customer.setPhoneNumber(RandomStringUtils.random(10, false, true));
            customer.setAddressLine1(RandomStringUtils.randomAlphabetic(15, 25));
            customer.setAddressLine2(RandomStringUtils.randomAlphabetic(15, 25));
            customer.setCity(RandomStringUtils.randomAlphabetic(4, 15));
            customer.setState(RandomStringUtils.randomAlphabetic(4, 8));
            customer.setZipCode(RandomStringUtils.random(5, false, true));
        }).collect(Collectors.toMap(Customer::getId, Function.identity()));
    }
}
