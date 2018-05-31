package guru.springframework.springmvccourse.services;

import guru.springframework.springmvccourse.domain.AbstractDomainObject;
import guru.springframework.springmvccourse.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
@Service
public class CustomerServiceImpl extends AbstractService implements CustomerService {

    @Override
    public List<AbstractDomainObject> getAll() {
        return super.getAll();
    }

    @Override
    public Customer get(Integer id) {
        Objects.requireNonNull(domainObjectMap.get(id), "Customer with specified id doesn't exist");
        return (Customer) super.get(id);
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        Objects.requireNonNull(customer, "Customer can't be null");
        return (Customer) super.saveOrUpdate(customer);
    }

    @Override
    public void delete(Integer id) {
        Objects.requireNonNull(domainObjectMap.get(id), "Customer with specified id doesn't exist");
       super.delete(id);
    }

    @Override
    protected void loadDomainObjects() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstName("Micheal");
        customer1.setLastName("Weston");
        customer1.setAddressLine1("1 Main St");
        customer1.setCity("Miami");
        customer1.setState("Florida");
        customer1.setZipCode("33101");
        customer1.setEmail("micheal@burnnotice.com");
        customer1.setPhoneNumber("305.333.0101");

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstName("Fiona");
        customer2.setLastName("Glenanne");
        customer2.setAddressLine1("1 Key Biscane Ave");
        customer2.setCity("Miami");
        customer2.setState("Florida");
        customer2.setZipCode("33101");
        customer2.setEmail("fiona@burnnotice.com");
        customer2.setPhoneNumber("305.323.0233");

        Customer customer3 = new Customer();
        customer3.setId(3);
        customer3.setFirstName("Sam");
        customer3.setLastName("Axe");
        customer3.setAddressLine1("1 Little Cuba Road");
        customer3.setCity("Miami");
        customer3.setState("Florida");
        customer3.setZipCode("33101");
        customer3.setEmail("sam@burnnotice.com");
        customer3.setPhoneNumber("305.426.9832");

        domainObjectMap.put(1, customer1);
        domainObjectMap.put(2, customer2);
        domainObjectMap.put(3, customer3);
    }
}
