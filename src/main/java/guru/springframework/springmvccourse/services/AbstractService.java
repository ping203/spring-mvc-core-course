package guru.springframework.springmvccourse.services;

import guru.springframework.springmvccourse.domain.AbstractDomainObject;

import java.util.*;

/**
 * Created by yriyMitsiuk on 31.05.2018.
 */
public abstract class AbstractService {

    protected Map<Integer, AbstractDomainObject> domainObjectMap;

    public AbstractService() {
        domainObjectMap = new HashMap<>();
        loadDomainObjects();
    }

    public List<AbstractDomainObject> getAll() {
        return new ArrayList<>(domainObjectMap.values());
    }

    public AbstractDomainObject get(Integer id) {
        return domainObjectMap.get(id);
    }

    public AbstractDomainObject saveOrUpdate(AbstractDomainObject object) {
        if (object.isNew()) {
            object.setId(getNextKey());
        }
        domainObjectMap.put(object.getId(), object);
        return object;
    }

    public void delete(Integer id) {
        domainObjectMap.remove(id);
    }

    private Integer getNextKey(){
        return Collections.max(domainObjectMap.keySet()) + 1;
    }

    protected abstract void loadDomainObjects();
}
