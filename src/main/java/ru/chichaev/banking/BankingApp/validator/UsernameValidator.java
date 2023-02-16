package ru.chichaev.banking.BankingApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chichaev.banking.BankingApp.repository.UserRepository;

@Component
public class UsernameValidator{

    private final UserRepository userRepository;

    public UsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String validate(String username) {
        if (userRepository.findByUsername(username).isPresent()){
            return "This username is already taken!";
        } else if (username.length() < 3 || username.length() > 16){
            return "Username should be from 3 to 16 characters long!";
        }
        return "";
    }
}
