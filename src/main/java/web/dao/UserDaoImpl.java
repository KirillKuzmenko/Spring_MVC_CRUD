package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.TypedQuery;
import javax.transaction.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entityManager.createQuery("update User set username = :username, password = :password" +
                ", firstname = :firstname, lastname = :lastname, age = :age" +
                " where id = :id")
                .setParameter("id", user.getId())
                .setParameter("username", user.getUsername())
                .setParameter("password", user.getPassword())
                .setParameter("firstname", user.getFirstname())
                .setParameter("lastname", user.getLastname())
                .setParameter("age", user.getAge())
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUser() {
        TypedQuery<User> users = entityManager.createQuery("from User", User.class);
        return users.getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void remoteUser(Long id) {
        entityManager.createQuery("delete from User where id = :id")
        .setParameter("id", id)
        .executeUpdate();
    }

    @Override
    public User findUserBuyUsername(String username) {
        return entityManager.createQuery("from User where username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public Role findRoleByName(String role) {
        return entityManager.createQuery("from Role where role = :role", Role.class)
                .setParameter("role", role)
                .getSingleResult();
    }
}
