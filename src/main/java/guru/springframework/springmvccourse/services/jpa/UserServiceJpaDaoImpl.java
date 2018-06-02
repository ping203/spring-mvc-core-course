package guru.springframework.springmvccourse.services.jpa;

import guru.springframework.springmvccourse.domain.User;
import guru.springframework.springmvccourse.services.UserService;
import guru.springframework.springmvccourse.services.security.EncryptionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Profile("jpa")
@Transactional(readOnly = true)
public class UserServiceJpaDaoImpl implements UserService {

    private EncryptionService encryptionService;

    public UserServiceJpaDaoImpl(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<?> getAll() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User get(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public User saveOrUpdate(User object) {
        if(object.getPassword() != null){
            object.setEncryptedPassword(encryptionService.encryptString(object.getPassword()));
        }
        return em.merge(object);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        em.remove(em.find(User.class, id));
    }
}
