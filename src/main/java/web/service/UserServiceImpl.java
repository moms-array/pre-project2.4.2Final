package web.service;

import org.springframework.stereotype.Service;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findUserById(Long id) {
        return null;
    }

    @Override
    public boolean saveUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    @Override
    public List<User> userList() {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public User findByUserName(String name) {
        return null;
    }
}
