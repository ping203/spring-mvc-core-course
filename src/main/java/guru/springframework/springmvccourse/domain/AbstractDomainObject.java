package guru.springframework.springmvccourse.domain;

import javax.persistence.*;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDomainObject implements HasID {

    @Id
    @GeneratedValue
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }
}
