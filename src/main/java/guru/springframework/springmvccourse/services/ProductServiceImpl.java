package guru.springframework.springmvccourse.services;

import guru.springframework.springmvccourse.domain.Product;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private Map<Integer, Product> products;

    public ProductServiceImpl() {
        loadProducts();
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product get(Integer id) {
        return products.get(id);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        Objects.requireNonNull(product, "Product can't be null");
        if (product.getId() == null) {
            product.setId(getNextKey());
        }
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public void delete(Integer id) {
        Objects.requireNonNull(products.get(id));
        products.remove(id);
    }

    private Integer getNextKey(){
        return Collections.max(products.keySet()) + 1;
    }

    private void loadProducts(){
        final AtomicInteger counter = new AtomicInteger();
        products = Stream.generate(Product::new).limit(5).peek(product -> {
            product.setId(counter.incrementAndGet());
            product.setDescription(RandomStringUtils.randomAlphabetic(5, 25));
            product.setImageUrl(RandomStringUtils.randomAlphabetic(5, 25));
            product.setPrice(BigDecimal.valueOf(RandomUtils.nextDouble(10, 100)).setScale(2, RoundingMode.HALF_UP));
        }).collect(Collectors.toMap(Product::getId, Function.identity()));
    }
}
