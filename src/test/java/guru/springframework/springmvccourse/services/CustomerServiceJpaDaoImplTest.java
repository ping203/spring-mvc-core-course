package guru.springframework.springmvccourse.services;

import guru.springframework.springmvccourse.config.JpaIntegrationConfig;
import guru.springframework.springmvccourse.domain.Customer;
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
        assertEquals("Miami", customer.getCity());
        assertEquals("33101", customer.getZipCode());
        assertEquals("micheal@burnnotice.com", customer.getEmail());
    }

    @Test
    public void testSave() {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Alex");
        newCustomer.setLastName("Burchuk");
        newCustomer.setAddressLine1("2 Main St");
        newCustomer.setCity("Miami");
        newCustomer.setState("Florida");
        newCustomer.setZipCode("33101");
        newCustomer.setEmail("alex@burnnotice.com");
        newCustomer.setPhoneNumber("305.333.0115");
        Customer savedCustomer = customerService.saveOrUpdate(newCustomer);
        assert savedCustomer.getId() != null;
        assertEquals(newCustomer.getFirstName(), savedCustomer.getFirstName());
        assertEquals(newCustomer.getLastName(), savedCustomer.getLastName());
        assertEquals(newCustomer.getCity(), savedCustomer.getCity());
    }

    @Test
    public void testUpdate() {
        Customer customer = customerService.get(6);
        customer.setFirstName("Micheal Updated");
        customer.setLastName("Weston Updated");
        customer.setCity("Miami Updated");
        assertEquals("Micheal Updated", customer.getFirstName());
        assertEquals("Weston Updated", customer.getLastName());
        assertEquals("Miami Updated", customer.getCity());
    }

    @Test
    public void testDelete() {
        Integer id = 7;
        customerService.delete(id);
        assert customerService.get(id) == null;
    }
}