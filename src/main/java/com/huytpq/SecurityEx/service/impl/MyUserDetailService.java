package com.huytpq.SecurityEx.service.impl;

import com.huytpq.SecurityEx.entity.Role;
import com.huytpq.SecurityEx.entity.User;
import com.huytpq.SecurityEx.entity.UserPrinciple;
import com.huytpq.SecurityEx.repo.RoleRepo;
import com.huytpq.SecurityEx.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepo roleRepo;

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        List<Role> roles = roleRepo.findRolesByUserId(user.getId());
        return new UserPrinciple(user,roles);
    }
}
