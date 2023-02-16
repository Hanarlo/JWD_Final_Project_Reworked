package ru.chichaev.banking.BankingApp.Controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.service.UserService;
import ru.chichaev.banking.BankingApp.validator.UserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final UserService userService;

    public AuthController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }


    @GetMapping("/register")
    public String register(@ModelAttribute("user") User user){
        return "auth/register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
    userValidator.validate(user, bindingResult);

    if (bindingResult.hasErrors()){
        return "/auth/register";
    }

    userService.saveUser(user);

    return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String login(){
        return "/auth/login";
    }
}
