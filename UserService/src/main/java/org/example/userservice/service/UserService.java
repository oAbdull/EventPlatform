package org.example.userservice.service;

import org.example.userservice.model.User;
import org.example.userservice.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveuser(User user) throws Exception {
        log.info("Saving user: {}", user.getUserid());
        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public Optional<User> findByUserId(String userid) {
        return userRepository.findByUserid(userid);
    }

    @Override
    public User getAuthenticatedUser() {
        String userid = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUserId(userid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userid));
    }

    @Override
    public List<User> listall() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        User user = findByUserId(userid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userid));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserid())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}