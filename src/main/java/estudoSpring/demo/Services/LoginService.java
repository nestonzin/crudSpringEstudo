package estudoSpring.demo.Services;

import estudoSpring.demo.Model.User;

public interface LoginService {

    User findByUsernameAndPassword(String username, String password);

    User findByToken(String token);

    String login(User user);
}
