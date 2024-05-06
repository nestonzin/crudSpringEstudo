package estudoSpring.demo.Repository;

import estudoSpring.demo.Model.User;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    User findByToken(String token);

    User findByUsername(String username);
}

