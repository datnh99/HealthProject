package HealthDeclaration.service.serviceImpl;

import java.util.ArrayList;

import HealthDeclaration.modal.entity.User;
import HealthDeclaration.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    IUserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByUsername(username);
        System.out.println(user.getPassword());
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), encoder.encode(user.getPassword()), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
