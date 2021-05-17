package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);
    boolean saveUser(User user);
    boolean deleteUser(Long id);
    List<User> userList();
    void addUser(User user);
    User findByUserName(String name);

}
