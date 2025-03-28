package com.huytpq.SecurityEx.recipe.service.impl;

import com.huytpq.SecurityEx.recipe.dto.RegisterInput;
import com.huytpq.SecurityEx.recipe.entity.Role;
import com.huytpq.SecurityEx.recipe.entity.User;
import com.huytpq.SecurityEx.recipe.entity.UserRole;
import com.huytpq.SecurityEx.recipe.repo.RoleRepo;
import com.huytpq.SecurityEx.recipe.repo.UserRepo;
import com.huytpq.SecurityEx.recipe.repo.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private RoleRepo roleRepo;

    public User register(RegisterInput input) {
        if (input.getUsername() == null || input.getPassword() == null || input.getRole() == null) {
                throw new IllegalArgumentException("Username, password, and role are required.");
        }

        // Kiểm tra nếu username đã tồn tại
        if (userRepo.findByUsername(input.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists.");
        }

        // Mã hóa password
        String encodedPassword = encoder.encode(input.getPassword());

        // Tạo user mới
        User user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(encodedPassword);
        user = userRepo.save(user); // Lưu user vào database
        user.getId();
        // Tìm role trong database
        Role role = roleRepo.findByName(input.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found: " + input.getRole()));

        // Lưu vào bảng user_role
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepo.save(userRole);
        return user;
    }

    public String verify(String username, String password) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if (auth.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            List<String> roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return JWTService.generateToken(username, roles);
        }
        return "fail";
    }
}
