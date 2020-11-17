package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.transaction.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUser() {
        List<User> users = entityManager.createQuery("SELECT u FROM User u").getResultList();
        return users;
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void remoteUser(Long id) {
        entityManager.remove(getUserById(id));
    }
}
