package estudoSpring.demo.Impl;

import estudoSpring.demo.Model.User;
import estudoSpring.demo.Repository.LoginRepository;
import estudoSpring.demo.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return loginRepository.findByUsernameAndPassword(username, password);
    }


    @Override
    public User findByToken(String token) {
        return loginRepository.findByToken(token);
    }


    @Override
    public String login(User user) {
        User foundUser = loginRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (foundUser != null) {
            // Gera um token único
            String token = UUID.randomUUID().toString();

            // Salva o token no usuário e persiste no banco de dados
            foundUser.setToken(token);
            loginRepository.save(foundUser);

            // Retorna o token
            return token;
        } else
            throw new RuntimeException("{\"message\": \"Login falhou\"}");
    }

}


