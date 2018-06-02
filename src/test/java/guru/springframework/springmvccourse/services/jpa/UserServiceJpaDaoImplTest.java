package guru.springframework.springmvccourse.services.jpa;

import guru.springframework.springmvccourse.config.JpaIntegrationConfig;
import guru.springframework.springmvccourse.domain.*;
import guru.springframework.springmvccourse.services.ProductService;
import guru.springframework.springmvccourse.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpa")
public class UserServiceJpaDaoImplTest {

    private UserService userService;
    private ProductService productService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void saveOrUpdate() {
        User user = getNewUser();
        User savedUser = userService.saveOrUpdate(user);

        assert savedUser.getId() != null;
        assert savedUser.getEncryptedPassword() != null;
        System.out.println("Encrypted Password -- " + savedUser.getEncryptedPassword());
    }

    @Test
    public void saveUserWithCustomer() {
        User user = getNewUser();
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

    @Test
    public void addCartToUser() {
        User user = getNewUser();
        user.setCart(new Cart());
        User savedUser = userService.saveOrUpdate(user);

        assert savedUser.getId() != null;
        assert savedUser.getCart() != null;
        assert savedUser.getCart().getId() != null;
        assert savedUser.getCart().getVersion() != null;
    }

    @Test
    public void addCartToUserWithCartDetails() {
        User user = getNewUser();
        Cart cart = new Cart();
        fillCart(cart);
        user.setCart(cart);

        User savedUser = userService.saveOrUpdate(user);

        assert savedUser.getId() != null;
        assert savedUser.getVersion() != null;
        assert savedUser.getCart() != null;
        assert savedUser.getCart().getCartDetails().size() == 2;
    }

    @Test
    public void addAndRemoveCartToUserWithCartDetails() {
        User user = getNewUser();
        Cart cart = new Cart();
        fillCart(cart);
        user.setCart(cart);

        User savedUser = userService.saveOrUpdate(user);

        assert savedUser.getCart().getCartDetails().size() == 2;

        savedUser.getCart().removeCartDetail(savedUser.getCart().getCartDetails().get(0));
        userService.saveOrUpdate(savedUser);

        assert savedUser.getCart().getCartDetails().size() == 1;

    }

    private User getNewUser() {
        User user = new User();
        user.setUsername("someusername");
        user.setPassword("myPassword");
        return user;
    }

    private void fillCart(@NotNull Cart cart) {
        List<Product> products = (List<Product>) productService.getAll();
        CartDetail cartDetail1 = new CartDetail();
        cartDetail1.setProduct(products.get(0));
        CartDetail cartDetail2 = new CartDetail();
        cartDetail1.setProduct(products.get(1));
        cart.addCartDetail(cartDetail1);
        cart.addCartDetail(cartDetail2);
    }

 }