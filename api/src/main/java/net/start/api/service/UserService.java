package net.start.api.service;

import net.start.api.model.Role;
import net.start.api.model.User;
import net.start.api.repo.RoleRepo;
import net.start.api.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

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
}
