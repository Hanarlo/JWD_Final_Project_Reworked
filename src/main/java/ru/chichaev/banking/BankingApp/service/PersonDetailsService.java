package ru.chichaev.banking.BankingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.repository.UserRepository;

import java.util.Optional;


@Service
public class PersonDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public PersonDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new ru.chichaev.banking.BankingApp.security.UserDetails(user.get());
    }
}
