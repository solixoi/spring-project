package net.api.srpring_kurses.repo;

import net.api.srpring_kurses.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
