package guru.springframework.springmvccourse.services.jpa;

import guru.springframework.springmvccourse.config.JpaIntegrationConfig;
import guru.springframework.springmvccourse.domain.Customer;
import guru.springframework.springmvccourse.domain.User;
import guru.springframework.springmvccourse.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpa")
public class UserServiceJpaDaoImplTest {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void saveOrUpdate() {
        User user = new User();
        user.setUsername("someusername");
        user.setPassword("myPassword");
        User savedUser = userService.saveOrUpdate(user);
        assert savedUser.getId() != null;
        assert savedUser.getEncryptedPassword() != null;
        System.out.println("Encrypted Password -- " + savedUser.getEncryptedPassword());
    }

    @Test
    public void saveUserWithCustomer() {
        User user = new User();
        user.setUsername("someusername");
        user.setPassword("myPassword");
        Customer customer = new Customer();
        customer.setFirstName("Alex");
        customer.setLastName("Barchuk");
        user.setCustomer(customer);
        User savedUser = userService.saveOrUpdate(user);
        assert savedUser.getId() != null;
        assert savedUser.getCustomer() != null;
        assert savedUser.getCustomer().getId() != null;

        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(customer.getFirstName(), savedUser.getCustomer().getFirstName());
    }
 }