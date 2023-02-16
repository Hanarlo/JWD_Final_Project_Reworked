package ru.chichaev.banking.BankingApp.validator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.repository.UserRepository;
import ru.chichaev.banking.BankingApp.security.UserDetails;

@Component
public class PasswordValidator {
    private final UserRepository userRepository;

    public PasswordValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean validate(String oldPassword, String newPassword) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (oldPassword == newPassword){
            return true;
        }
        if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
                return true;
        }
        return false;
    }
}