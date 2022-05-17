package dms.study.security.service;

import dms.study.security.entity.User;
import dms.study.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder bycriptEncoder;

    public User findUserByName(String name) {
        return userRepository.findByUserName(name);
    }

    public User addUser(User user) {
        user.setPassword(bycriptEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
