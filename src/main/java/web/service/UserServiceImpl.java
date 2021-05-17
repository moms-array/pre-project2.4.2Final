package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.security.MyUserDetailService;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    MyUserDetailService myUserDetailService;

    @Autowired
    public void setMyUserDetailService(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @Override
    public User findUserById(Long id) {
        return myUserDetailService.findUserById(id);
    }

    @Override
    public boolean saveUser(User user) {
        return myUserDetailService.saveUser(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return myUserDetailService.deleteUser(id);
    }

    @Override
    public List<User> userList() {
        return myUserDetailService.userList();
    }

    @Override
    public void addUser(User user) {
        myUserDetailService.addUser(user);
    }

    @Override
    public User findByUserName(String name) {
        return myUserDetailService.findByUsername(name);
    }
}
