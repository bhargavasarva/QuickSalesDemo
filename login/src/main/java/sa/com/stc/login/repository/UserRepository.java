package sa.com.stc.login.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sa.com.stc.login.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}
