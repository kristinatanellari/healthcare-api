package main.healthcare.api.service;

import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Role;
import main.healthcare.api.model.Users;
import main.healthcare.api.repository.RoleRepository;
import main.healthcare.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users registerUser(String username, String password, String roleName) throws ValidateDataException {
        Users user = userRepository.findByUsername(username);
        Users newUser;
        if (user != null) {
            throw new ValidateDataException("Account already exists with this username!");
        } else {
            newUser = new Users();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setRole(roleName);
        }

        return userRepository.save(newUser);
    }

    public void updateUser(String oldUsername, String username, String password, String roleName) throws ValidateDataException{
        Users user = userRepository.findByUsername(oldUsername);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(roleName);
        userRepository.save(user);
    }

}
