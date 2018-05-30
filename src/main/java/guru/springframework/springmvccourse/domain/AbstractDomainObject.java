package guru.springframework.springmvccourse.domain;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
public abstract class AbstractDomainObject implements HasID {
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
