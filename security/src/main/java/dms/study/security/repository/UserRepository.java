package dms.study.security.repository;

import dms.study.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String username);
}
