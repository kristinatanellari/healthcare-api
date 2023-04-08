package main.healthcare.api.security;

import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Users;
import main.healthcare.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            try {
                throw new ValidateDataException("Username is not valid.");
            } catch (ValidateDataException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }
}
