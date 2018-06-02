package guru.springframework.springmvccourse.services.map;

import guru.springframework.springmvccourse.domain.AbstractDomainObject;
import guru.springframework.springmvccourse.domain.Product;
import guru.springframework.springmvccourse.services.ProductService;
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
}
