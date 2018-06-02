package guru.springframework.springmvccourse.services.jpa;

import guru.springframework.springmvccourse.config.JpaIntegrationConfig;
import guru.springframework.springmvccourse.domain.Product;
import guru.springframework.springmvccourse.services.ProductService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by yriyMitsiuk on 01.06.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpa")
public class ProductServiceJpaDaoImplTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testGetAll() {
        List<Product> products = (List<Product>) productService.getAll();
        assert products.size() == 5;
    }

    @Test
    public void testGet() {
        Product product = productService.get(1);
        assertEquals("Product 1", product.getDescription());
        assertEquals("http://example.com/product1", product.getImageUrl());
    }

    @Test
    public void testSave() {
        Product product = new Product();
        product.setDescription("Product 6");
        product.setPrice(new BigDecimal("17.75"));
        product.setImageUrl("http://example.com/product6");
        Product savedProduct = productService.saveOrUpdate(product);
        assertEquals(product.getDescription(),savedProduct.getDescription());
        assertEquals(product.getImageUrl(), savedProduct.getImageUrl());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        productService.delete(savedProduct.getId());
    }

    @Test
    public void testUpdate() {
        Product product = productService.get(1);
        product.setDescription("Product 1 Updated");
        product.setPrice(new BigDecimal("17.75"));
        product.setImageUrl("http://example.com/product6/updated");
        productService.saveOrUpdate(product);
        assertEquals(product.getDescription(), productService.get(1).getDescription());
        assertEquals(product.getImageUrl(), productService.get(1).getImageUrl());
        assertEquals(product.getPrice(), productService.get(1).getPrice());
    }
}