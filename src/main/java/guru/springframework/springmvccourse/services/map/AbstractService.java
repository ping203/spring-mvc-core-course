package guru.springframework.springmvccourse.services.map;

import guru.springframework.springmvccourse.domain.AbstractDomainObject;

import java.util.*;

/**
 * Created by yriyMitsiuk on 31.05.2018.
 */
public abstract class AbstractService {

    protected Map<Integer, AbstractDomainObject> domainObjectMap;

    public AbstractService() {
        domainObjectMap = new HashMap<>();
    }

    public List<AbstractDomainObject> getAll() {
        return new ArrayList<>(domainObjectMap.values());
    }

    public AbstractDomainObject get(Integer id) {
        return domainObjectMap.get(id);
    }

    public AbstractDomainObject saveOrUpdate(AbstractDomainObject object) {
        if (object.getId() == null) {
            object.setId(getNextKey());
        }
        domainObjectMap.put(object.getId(), object);
        return object;
    }

    public void delete(Integer id) {
        domainObjectMap.remove(id);
    }

    private Integer getNextKey(){
        if (domainObjectMap.size() == 0) return 1;
        return Collections.max(domainObjectMap.keySet()) + 1;
    }
}
