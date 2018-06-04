package guru.springframework.springmvccourse.services.jpa;

import guru.springframework.springmvccourse.config.JpaIntegrationConfig;
import guru.springframework.springmvccourse.domain.Order;
import guru.springframework.springmvccourse.domain.OrderDetail;
import guru.springframework.springmvccourse.enums.OrderStatus;
import guru.springframework.springmvccourse.services.OrderService;
import guru.springframework.springmvccourse.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpa")
public class OrderServiceJpaDaoImplTest {

    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testGetAll() {
        List<Order> all = (List<Order>) orderService.getAll();
        assert  all.size() > 0;
    }

    @Test
    public void testGet() {
        Order order = orderService.get(9);
        assertEquals(OrderStatus.NEW, order.getStatus());
        assertEquals("Miami", order.getShippingAddress().getCity());
        assertEquals("Florida", order.getShippingAddress().getState());
    }

    @Test
    public void testSaveOrUpdate() {
        Order order = orderService.get(9);
        order.setStatus(OrderStatus.SHIPPED);
        order.getShippingAddress().setZipCode("30102");

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setQuantity(10);
        orderDetail1.setOrder(order);
        orderDetail1.setProduct(productService.get(1));
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setQuantity(5);
        orderDetail2.setOrder(order);
        orderDetail2.setProduct(productService.get(1));

        order.setOrderDetails(Arrays.asList(orderDetail1, orderDetail2));
        Order returnedOrder = orderService.saveOrUpdate(order);

        assertEquals(OrderStatus.SHIPPED, returnedOrder.getStatus());
        assertEquals("30102", returnedOrder.getShippingAddress().getZipCode());
        assert returnedOrder.getOrderDetails().size() == 2;
    }

    @Test
    public void testDelete() {
        Integer id = 10;
        orderService.delete(id);
        assert orderService.get(id) == null;
    }
}