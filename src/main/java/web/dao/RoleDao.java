package web.dao;

import web.model.Role;

public interface RoleDao {
    Role findRoleByName(String name);

}
