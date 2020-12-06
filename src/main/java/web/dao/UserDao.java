package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void updateUser(User user);
    List<User> listUser();
    User getUserById(Long id);
    void remoteUser(Long id);
    User findUserBuyUsername(String username);
    Role findRoleByName(String name);
}
