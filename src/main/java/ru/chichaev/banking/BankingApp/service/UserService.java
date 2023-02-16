package ru.chichaev.banking.BankingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.repository.RoleRepository;
import ru.chichaev.banking.BankingApp.repository.UserRepository;
import ru.chichaev.banking.BankingApp.security.UserDetails;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Boolean isExists(User user){
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        return userOptional.isPresent();
    }

    @Transactional
    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.getReferenceById(1));
        userRepository.save(user);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
