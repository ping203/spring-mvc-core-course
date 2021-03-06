package guru.springframework.springmvccourse.bootstrap;

import guru.springframework.springmvccourse.domain.*;
import guru.springframework.springmvccourse.enums.OrderStatus;
import guru.springframework.springmvccourse.services.CustomerService;
import guru.springframework.springmvccourse.services.OrderService;
import guru.springframework.springmvccourse.services.ProductService;
import guru.springframework.springmvccourse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * Created by yriyMitsiuk on 01.06.2018.
 */
@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ProductService productService;
    private UserService userService;
    private OrderService orderService;
    private CustomerService customerService;

    @Autowired
    public SpringJPABootstrap(ProductService productService, UserService userService, OrderService orderService, CustomerService customerService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadProducts();
        loadCustomers();
        loadOrders();
    }

    private void loadProducts() {
        Product product1 = new Product();
        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal("12.99"));
        product1.setImageUrl("http://example.com/product1");
        productService.saveOrUpdate(product1);

        Product product2 = new Product();
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("14.99"));
        product2.setImageUrl("http://example.com/product2");
        productService.saveOrUpdate(product2);

        Product product3 = new Product();
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal("34.99"));
        product3.setImageUrl("http://example.com/product3");
        productService.saveOrUpdate(product3);

        Product product4 = new Product();
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal("44.99"));
        product4.setImageUrl("http://example.com/product4");
        productService.saveOrUpdate(product4);

        Product product5 = new Product();
        product5.setDescription("Product 5");
        product5.setPrice(new BigDecimal("25.99"));
        product5.setImageUrl("http://example.com/product5");
        productService.saveOrUpdate(product5);
    }

    private void loadCustomers() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");

        Customer customer1 = new Customer();
        customer1.setFirstName("Micheal");
        customer1.setLastName("Weston");
        customer1.setBillingAddress(new Address());
        customer1.getBillingAddress().setAddressLine1("1 Main St");
        customer1.getBillingAddress().setCity("Miami");
        customer1.getBillingAddress().setState("Florida");
        customer1.getBillingAddress().setZipCode("33101");
        customer1.setEmail("micheal@burnnotice.com");
        customer1.setPhoneNumber("305.333.0101");
        user1.setCustomer(customer1);
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");

        Customer customer2 = new Customer();
        customer2.setFirstName("Fiona");
        customer2.setLastName("Glenanne");
        customer2.setBillingAddress(new Address());
        customer2.getBillingAddress().setAddressLine1("1 Key Biscane Ave");
        customer2.getBillingAddress().setCity("Miami");
        customer2.getBillingAddress().setState("Florida");
        customer2.getBillingAddress().setZipCode("33101");
        customer2.setEmail("fiona@burnnotice.com");
        customer2.setPhoneNumber("305.323.0233");
        user2.setCustomer(customer2);
        userService.saveOrUpdate(user2);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword("password3");

        Customer customer3 = new Customer();
        customer3.setFirstName("Sam");
        customer3.setLastName("Axe");
        customer3.setBillingAddress(new Address());
        customer3.getBillingAddress().setAddressLine1("1 Little Cuba Road");
        customer3.getBillingAddress().setCity("Miami");
        customer3.getBillingAddress().setState("Florida");
        customer3.getBillingAddress().setZipCode("33101");
        customer3.setEmail("sam@burnnotice.com");
        customer3.setPhoneNumber("305.426.9832");
        user3.setCustomer(customer3);
        userService.saveOrUpdate(user3);
    }

    private void loadOrders() {
        List<User> users = (List<User>) userService.getAll();
        List<Product> products = (List<Product>) productService.getAll();

        users.forEach(user ->{
            Order order = new Order();
            order.setCustomer(user.getCustomer());
            order.setStatus(OrderStatus.SHIPPED);
            orderService.saveOrUpdate(order);

            products.forEach(product -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(1);
                order.addToOrderDetails(orderDetail);
            });
        });
//        Order order1 = new Order();
//        order1.setCustomer(customerService.get(8));
//        order1.setDateShipped(LocalDate.of(2018, Month.AUGUST, 23));
//        order1.setShippingAddress(new Address());
//        order1.getShippingAddress().setAddressLine1("1 Key Biscane Ave");
//        order1.getShippingAddress().setCity("Miami");
//        order1.getShippingAddress().setState("Florida");
//        order1.getShippingAddress().setZipCode("33101");
//        order1.setStatus(OrderStatus.NEW);
//        orderService.saveOrUpdate(order1);
//
//        Order order2 = new Order();
//        order2.setCustomer(customerService.get(8));
//        order2.setDateShipped(LocalDate.of(2018, Month.AUGUST, 15));
//        order2.setShippingAddress(new Address());
//        order2.getShippingAddress().setAddressLine1("1 Key Biscane Ave");
//        order2.getShippingAddress().setCity("Miami");
//        order2.getShippingAddress().setState("Florida");
//        order2.getShippingAddress().setZipCode("33101");
//        order2.setStatus(OrderStatus.ALLOCATED);
//        orderService.saveOrUpdate(order2);
    }
}
