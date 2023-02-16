package ru.chichaev.banking.BankingApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.service.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(userService.isExists((User) target)){
            errors.rejectValue("username", "", "User with this username is already exists!");
        }
        return;
    }
}
