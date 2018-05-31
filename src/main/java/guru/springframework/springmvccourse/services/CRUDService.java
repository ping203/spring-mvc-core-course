package guru.springframework.springmvccourse.services;

import java.util.List;

/**
 * Created by yriyMitsiuk on 31.05.2018.
 */
public interface CRUDService<T> {
    List<?> getAll();
    T get(Integer id);
    T saveOrUpdate(T object);
    void delete(Integer id);
}
