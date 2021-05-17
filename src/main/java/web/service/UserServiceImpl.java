package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;
import web.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long id){
        return userRepository.findById(id).get();
    }

    @Override
    public boolean saveUser(User user){
        User userFromBd = null;
        if(userRepository.findById(user.getId()).isPresent()) {
            userFromBd = userRepository.findById(user.getId()).get();
        }
        if(userFromBd == null){
            return false;
        }
        user.setRoles(Collections.singleton(new Role(2L,"USER")));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> userList(){
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user){
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String name){
        return userRepository.findByUsername(name);
    }

}
