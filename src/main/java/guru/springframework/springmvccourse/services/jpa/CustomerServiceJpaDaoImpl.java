package guru.springframework.springmvccourse.services.jpa;

import guru.springframework.springmvccourse.domain.Customer;
import guru.springframework.springmvccourse.services.CustomerService;
import guru.springframework.springmvccourse.services.security.EncryptionService;
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
public class CustomerServiceJpaDaoImpl implements CustomerService {

    @PersistenceContext
    private EntityManager em;

    private EncryptionService encryptionService;

    public CustomerServiceJpaDaoImpl(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public List<Customer> getAll() {
        return em.createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    public Customer get(Integer id) {
        return em.find(Customer.class, id);
    }

    @Override
    @Transactional
    public Customer saveOrUpdate(Customer object) {
        if (object.getUser() != null && object.getUser().getPassword() != null) {
            object.getUser().setEncryptedPassword(encryptionService.encryptString(object.getUser().getPassword()));
        }
        return em.merge(object);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        em.remove(em.find(Customer.class, id));
    }
}
