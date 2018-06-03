package guru.springframework.springmvccourse.services.jpa;

import guru.springframework.springmvccourse.domain.Order;
import guru.springframework.springmvccourse.services.OrderService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Profile("jpa")
@Transactional(readOnly = true)
public class OrderServiceJpaDaoImpl implements OrderService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> getAll() {
        return em.createQuery("from Order", Order.class).getResultList();
    }

    @Override
    public Order get(Integer id) {
        return em.find(Order.class, id);
    }

    @Override
    @Transactional
    public Order saveOrUpdate(Order order) {
        return em.merge(order);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        em.remove(em.find(Order.class, id));
    }
}
