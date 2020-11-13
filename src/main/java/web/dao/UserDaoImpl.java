package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;

    @Autowired
    private void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public void addUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<User> listUser() {
        return (List<User>) entityManager.createNativeQuery("User.getAll", User.class).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void remoteUser(Long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(getUserById(id));
        entityManager.getTransaction().commit();
    }
}
