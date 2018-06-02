package guru.springframework.springmvccourse.services.map;

import guru.springframework.springmvccourse.domain.AbstractDomainObject;
import guru.springframework.springmvccourse.domain.User;
import guru.springframework.springmvccourse.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Profile("map")
public class UserServiceImpl extends AbstractService implements UserService {

    @Override
    public List<AbstractDomainObject> getAll() {
        return super.getAll();
    }

    @Override
    public User get(Integer id) {
        Objects.requireNonNull(domainObjectMap.get(id), "User with specified id doesn't exist");
        return (User) super.get(id);
    }

    @Override
    public void delete(Integer id) {
        Objects.requireNonNull(domainObjectMap.get(id), "User with specified id doesn't exist");
        super.delete(id);
    }

    @Override
    public User saveOrUpdate(User object) {
        Objects.requireNonNull(object, "User can't be null");
        return (User) super.saveOrUpdate(object);
    }
}
