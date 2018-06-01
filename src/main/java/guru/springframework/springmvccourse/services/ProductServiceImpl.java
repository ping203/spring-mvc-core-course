package guru.springframework.springmvccourse.services;

import guru.springframework.springmvccourse.domain.AbstractDomainObject;
import guru.springframework.springmvccourse.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
@Service
@Profile("map")
public class ProductServiceImpl extends AbstractService implements ProductService {

    @Override
    public List<AbstractDomainObject> getAll() {
        return super.getAll();
    }

    @Override
    public Product get(Integer id) {
        return (Product) super.get(id);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        Objects.requireNonNull(product, "Product can't be null");
        return (Product) super.saveOrUpdate(product);
    }

    @Override
    public void delete(Integer id) {
        Objects.requireNonNull(domainObjectMap.get(id));
        super.delete(id);
    }

    @Override
    protected void loadDomainObjects(){
        Product product1 = new Product();
        product1.setId(1);
        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal("12.99"));
        product1.setImageUrl("http://example.com/product1");

        domainObjectMap.put(1, product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("14.99"));
        product2.setImageUrl("http://example.com/product2");

        domainObjectMap.put(2, product2);

        Product product3 = new Product();
        product3.setId(3);
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal("34.99"));
        product3.setImageUrl("http://example.com/product3");

        domainObjectMap.put(3, product3);

        Product product4 = new Product();
        product4.setId(4);
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal("44.99"));
        product4.setImageUrl("http://example.com/product4");

        domainObjectMap.put(4, product4);

        Product product5 = new Product();
        product5.setId(5);
        product5.setDescription("Product 5");
        product5.setPrice(new BigDecimal("25.99"));
        product5.setImageUrl("http://example.com/product5");

        domainObjectMap.put(5, product5);
    }
}
