package net.api.srpring_kurses.service;

import net.api.srpring_kurses.dto.admin.UserDTO;
import net.api.srpring_kurses.model.Role;
import net.api.srpring_kurses.model.User;
import net.api.srpring_kurses.repo.RoleRepo;
import net.api.srpring_kurses.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final JWTService jwtService;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authManager;
    private final RoleRepo roleRepo;

    public UserService(UserRepo userRepo, AuthenticationManager authenticationManager, JWTService jwtService, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.authManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleRepo = roleRepo;
    }

    public User register(User user) {
        if(userRepo.findByUsername(user.getUsername()) != null || userRepo.findByEmail(user.getEmail()) != null) {
            return null;
        }
        user.setPassword(encoder.encode(user.getPassword()));
        Role role = roleRepo.findByName("STUDENT");
        user.setRoles(Set.of(role));
        return userRepo.save(user);
    }

    public String verify(User user) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(auth.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "Fail";
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList()) ;
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }
}
