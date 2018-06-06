package guru.springframework.springmvccourse.services.jpa;

import guru.springframework.springmvccourse.config.JpaIntegrationConfig;
import guru.springframework.springmvccourse.domain.Address;
import guru.springframework.springmvccourse.domain.Customer;
import guru.springframework.springmvccourse.domain.User;
import guru.springframework.springmvccourse.services.CustomerService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yriyMitsiuk on 01.06.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpa")
public class CustomerServiceJpaDaoImplTest {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Test
    public void testGetAll() {
        List<Customer> customers = (List<Customer>) customerService.getAll();
        assert customers.size() > 0;
    }

    @Test
    public void testGet() {
        Customer customer = customerService.get(6);
        assertEquals("Micheal", customer.getFirstName());
        assertEquals("Weston", customer.getLastName());
        assertEquals("micheal@burnnotice.com", customer.getEmail());
        assertEquals("Miami", customer.getBillingAddress().getCity());
        assertEquals("33101", customer.getBillingAddress().getZipCode());
    }

    @Test
    public void testSave() {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Alex");
        newCustomer.setLastName("Burchuk");
        newCustomer.setBillingAddress(new Address());
        newCustomer.getBillingAddress().setAddressLine1("1 Main St");
        newCustomer.getBillingAddress().setCity("Miami");
        newCustomer.getBillingAddress().setState("Florida");
        newCustomer.getBillingAddress().setZipCode("33101");
        newCustomer.setEmail("alex@burnnotice.com");
        newCustomer.setPhoneNumber("305.333.0115");

        User newUser = new User();
        newUser.setUsername("This is my user name");
        newUser.setPassword("MyAwesomePassword");
        newCustomer.setUser(newUser);

        Customer savedCustomer = customerService.saveOrUpdate(newCustomer);
        assert savedCustomer.getId() != null;
        assert savedCustomer.getUser().getId() != null;
        assertEquals(newCustomer.getFirstName(), savedCustomer.getFirstName());
        assertEquals(newCustomer.getLastName(), savedCustomer.getLastName());
        assertEquals(newCustomer.getBillingAddress().getCity(), savedCustomer.getBillingAddress().getCity());
    }

    @Test
    public void testUpdate() {
        Customer customer = customerService.get(6);
        customer.setFirstName("Micheal Updated");
        customer.setLastName("Weston Updated");
        customer.setBillingAddress(new Address());
        customer.getBillingAddress().setAddressLine1("1 Main St");
        customer.getBillingAddress().setCity("Miami");
        customer.getBillingAddress().setState("Florida");
        customer.getBillingAddress().setZipCode("33101");
        Customer updatedCustomer = customerService.saveOrUpdate(customer);
        assertEquals("Micheal Updated", updatedCustomer.getFirstName());
        assertEquals("Weston Updated", updatedCustomer.getLastName());
        assertEquals("Miami", updatedCustomer.getBillingAddress().getCity());
    }

    @Test
    @Ignore
    public void testDelete() {
        Integer id = 7;
        customerService.delete(id);
        assert customerService.get(id) == null;
    }
}