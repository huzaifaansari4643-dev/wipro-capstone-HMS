package com.hms.auth.services.auth;



import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hms.auth.dto.SignupRequest;
import com.hms.auth.dto.UserDto;
import com.hms.auth.entity.User;
import com.hms.auth.enums.UserRole;
import com.hms.auth.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount() {
        Optional<User> optionalUser = userRepository.findByUserRole(UserRole.ADMIN);
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto signupUser(SignupRequest signupRequest) {
        if (userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        // Restrict ADMIN role to admin creation
        if (signupRequest.getRole() == UserRole.ADMIN) {
            throw new IllegalStateException("ADMIN role can only be created by admin");
        }
        user.setUserRole(signupRequest.getRole() != null ? signupRequest.getRole() : UserRole.PATIENT); // Default to PATIENT
        userRepository.save(user);
        return user.getUserDto();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}