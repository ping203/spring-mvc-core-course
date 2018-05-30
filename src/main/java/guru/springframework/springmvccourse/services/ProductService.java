package guru.springframework.springmvccourse.services;

import guru.springframework.springmvccourse.domain.Product;

import java.util.List;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
public interface ProductService {

    List<Product> getAll();

}
