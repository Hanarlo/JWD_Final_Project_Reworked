package ru.chichaev.banking.BankingApp.validator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chichaev.banking.BankingApp.entity.Bill;
import ru.chichaev.banking.BankingApp.entity.Payment;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.security.UserDetails;
import ru.chichaev.banking.BankingApp.service.BillService;
import ru.chichaev.banking.BankingApp.service.UserService;

@Component
public class PaymentValidator implements Validator {

    private final UserService userService;
    private final BillService billService;

    public PaymentValidator(UserService userService, BillService billService) {
        this.userService = userService;
        this.billService = billService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Payment.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Payment payment = (Payment) target;
        if (userService.getUserByUsername(payment.getReceiverLogin()).isPresent()){
            if (billService.isExists(userService.getUserByUsername(payment.getReceiverLogin()).get(),
                    payment.getReceiverBillName()).isPresent()){
                Bill bill = billService.isExists(userService.getUserByUsername(payment.getReceiverLogin()).get(),
                        payment.getReceiverBillName()).get();
                if (!bill.isBlocked()){
                    Bill senderBill = billService.getBillById(Integer.parseInt(payment.getSenderBillId()));
                    if (!payment.getAmount().isEmpty()){
                        if (senderBill.getBalance() >= Integer.parseInt(payment.getAmount())){
                            return;
                        } else {
                            errors.rejectValue("amount", "", "There are not enough money on the account");
                        }
                    } else {
                        errors.rejectValue("amount", "", "Please input amount");
                    }
                } else {
                    errors.rejectValue("receiverBillName", "", "This bill is blocked!");
                }
            } else {
                errors.rejectValue("receiverBillName", "", "Bill with that name is not found on user " + payment.getReceiverLogin());
            }
        } else {
            errors.rejectValue("receiverLogin", "", "User with this username is not found!");
        }
    }
}
