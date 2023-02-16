package ru.chichaev.banking.BankingApp.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chichaev.banking.BankingApp.entity.Bill;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.security.UserDetails;
import ru.chichaev.banking.BankingApp.service.BillService;
import ru.chichaev.banking.BankingApp.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final BillService billService;

    public AdminController(UserService userService, BillService billService) {
        this.userService = userService;
        this.billService = billService;
    }

    @GetMapping
    public String mainPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "/admin/AdminMainPage";
    }

    @GetMapping("/unblock")
    public String restoreBill(){
        return "/admin/RestoreBillPage";
    }

    @PostMapping("/unblock")
    public String restoreBill(@RequestParam("bill") String billName, @RequestParam("username") String username){
        if (userService.getUserByUsername(username).isEmpty()){
            return "redirect:/admin/unblock?usernameError";
        }
        User user = userService.getUserByUsername(username).get();
        if (billService.isExists(user, billName).isEmpty()){
            return "redirect:/admin/unblock?billError";
        }
        Bill bill = billService.isExists(user, billName).get();
        bill.setBlocked(false);
        billService.save(bill);
        return "redirect:/admin";
    }

    @GetMapping("/restore")
    public String restoreUser(){
        return "/admin/RestoreUserPage";
    }


    @PostMapping("/restore")
    public String restoreUser(@RequestParam("username") String username){

       if (userService.getUserByUsername(username).isEmpty()){
           return "redirect:/admin/restore?error";
       }
       User user =  userService.getUserByUsername(username).get();
       user.setBlocked(false);
       userService.saveUser(user);
       return "redirect:/admin";
    }
}
