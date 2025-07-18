package src.danik.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.danik.userservice.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameLike(String username);
    
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
