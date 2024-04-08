package estudoSpring.demo.Controller;

import estudoSpring.demo.Model.User;
import estudoSpring.demo.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return loginService.login(user);
    }
}
