package ru.chichaev.banking.BankingApp.Controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chichaev.banking.BankingApp.entity.Bill;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.repository.BillRepository;
import ru.chichaev.banking.BankingApp.security.UserDetails;
import ru.chichaev.banking.BankingApp.service.HistoryService;
import ru.chichaev.banking.BankingApp.service.UserService;
import ru.chichaev.banking.BankingApp.validator.PasswordValidator;
import ru.chichaev.banking.BankingApp.validator.UsernameValidator;

import java.util.ArrayList;

@org.springframework.stereotype.Controller
@RequestMapping()
public class UserController {

    private final BillRepository billRepository;
    private final UserService userService;
    private final UsernameValidator usernameValidator;
    private final PasswordValidator passwordValidator;
    private final HistoryService historyService;

    public UserController(BillRepository billRepository,
                          UserService userService,
                          UsernameValidator usernameValidator,
                          PasswordValidator passwordValidator, HistoryService historyService) {
        this.billRepository = billRepository;
        this.userService = userService;
        this.usernameValidator = usernameValidator;
        this.passwordValidator = passwordValidator;
        this.historyService = historyService;
    }

    @GetMapping("/main")
    public String mainPage(Model model){
        User user = getUserDetails().getUser();
        model.addAttribute("user", user);
        ArrayList<Bill> billList = billRepository.getBillsByUser(user);
        model.addAttribute("bills", billList);
        return "/MainPage";
    }

    @GetMapping("/change_password")
    public String changePassword(){return "/ChangePassword";}

    @PostMapping("/change_password")
    public String changePassword(@RequestParam("old_password") String oldPassword,
                                 @RequestParam("new_password") String newPassword,
                                 Model model
    ){
        UserDetails userDetails = getUserDetails();
        User user = userDetails.getUser();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (passwordValidator.validate(oldPassword,newPassword)){
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userService.save(user);
            userDetails.setPassword(bCryptPasswordEncoder.encode(newPassword));
        } else {
            return "redirect:/change_password?error";
        }
        return "redirect:/main";

    }

    @GetMapping("/change_username")
    public String changeUsername(){return "/ChangeUsername";}

    @PostMapping("/change_username")
    public String changeUsername(@RequestParam("username") String username, Model model){
        UserDetails userDetails = getUserDetails();
        User user = userDetails.getUser();
        String result = usernameValidator.validate(username);
        if (result.isEmpty()){
            user.setUsername(username);
            userService.save(user);
            userDetails.setUsername(username);
        } else {
            model.addAttribute("error", result);
            return "/ChangeUsername";
        }
        return "redirect:/main";
    }

    @GetMapping("/delete")
    public String deleteUser(){
        UserDetails userDetails = getUserDetails();
        User user = userDetails.getUser();
        user.setBlocked(true);
        userService.save(user);
        userDetails.setBlocked(true);
        return "redirect:/logout";
    }

    @GetMapping("/history")
    public String history(Model model){
        model.addAttribute("outgoingHistory",
                historyService.getOutgoingHistory(getUserDetails().getUser()));
        model.addAttribute("incomingHistory",
                historyService.getIncomingHistory(getUserDetails().getUser()));
        return "/History";
    }

    private UserDetails getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }


}
