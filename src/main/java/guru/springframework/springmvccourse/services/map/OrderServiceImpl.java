package guru.springframework.springmvccourse.services.map;

import guru.springframework.springmvccourse.domain.AbstractDomainObject;
import guru.springframework.springmvccourse.domain.Order;
import guru.springframework.springmvccourse.services.OrderService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Profile("map")
public class OrderServiceImpl extends AbstractService implements OrderService {

    @Override
    public List<AbstractDomainObject> getAll() {
        return super.getAll();
    }

    @Override
    public Order get(Integer id) {
        Objects.requireNonNull(domainObjectMap.get(id), "Order with specified id doesn't exist");
        return (Order) super.get(id);
    }

    @Override
    public void delete(Integer id) {
        Objects.requireNonNull(domainObjectMap.get(id), "Order with specified id doesn't exist");
        super.delete(id);
    }

    @Override
    public Order saveOrUpdate(Order object) {
        Objects.requireNonNull(object, "Order can't be null");
        return (Order) super.saveOrUpdate(object);
    }
}
