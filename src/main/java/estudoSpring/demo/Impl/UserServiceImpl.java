package estudoSpring.demo.Impl;

import estudoSpring.demo.Model.User;
import estudoSpring.demo.Repository.UserRepository;
import estudoSpring.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserRepository userRepository;
    @Override
    public User create(User user) {
        User existUser = userRepository.findByUsername(user.getUsername());

        if(existUser != null) {
            throw new Error("Usuario ja existe!");
        }

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        return createdUser;
    }
}
