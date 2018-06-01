package guru.springframework.springmvccourse.services;

import guru.springframework.springmvccourse.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by yriyMitsiuk on 01.06.2018.
 */
@Service
@Profile("jpa")
@Transactional(readOnly = true)
public class ProductServiceJpaDaoImpl implements ProductService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> getAll() {
        return em.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product get(Integer id) {
        return em.find(Product.class, id);
    }

    @Override
    @Transactional
    public Product saveOrUpdate(Product object) {
        return em.merge(object);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        em.remove(em.find(Product.class, id));
    }
}
