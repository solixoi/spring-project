package net.api.srpring_kurses.repo;

import net.api.srpring_kurses.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query(value = "SELECT r.name FROM users u " +
            "INNER JOIN user_roles ur ON u.id = ur.user_id " +
            "INNER JOIN roles r ON ur.role_id = r.id " +
            "WHERE u.username = :username", nativeQuery = true)
    String findRoleByUsername(@Param("username") String username);
    User findByUsername(String username);
    User findByEmail(String email);

    @Query("SELECT u.id FROM User u " +
            "WHERE u.username = :username")
    Long findIdByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findByIdDirect(Long id);
}
