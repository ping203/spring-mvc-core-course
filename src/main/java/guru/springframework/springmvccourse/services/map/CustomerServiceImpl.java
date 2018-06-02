package guru.springframework.springmvccourse.services.map;

import guru.springframework.springmvccourse.domain.AbstractDomainObject;
import guru.springframework.springmvccourse.domain.Customer;
import guru.springframework.springmvccourse.services.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
@Service
@Profile("map")
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
}
