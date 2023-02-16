package ru.chichaev.banking.BankingApp.Controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chichaev.banking.BankingApp.entity.Bill;
import ru.chichaev.banking.BankingApp.entity.Payment;
import ru.chichaev.banking.BankingApp.service.BillService;
import ru.chichaev.banking.BankingApp.service.HistoryService;
import ru.chichaev.banking.BankingApp.service.UserService;
import ru.chichaev.banking.BankingApp.validator.BillValidator;
import ru.chichaev.banking.BankingApp.validator.PaymentValidator;

@Controller
@RequestMapping
public class BillController {
    private final BillService billService;
    private final BillValidator billValidator;
    private final PaymentValidator paymentValidator;
    private final UserService userService;
    private final HistoryService historyService;

    public BillController(BillService billService, BillValidator billValidator, PaymentValidator paymentValidator, UserService userService, HistoryService historyService) {
        this.billService = billService;
        this.billValidator = billValidator;
        this.paymentValidator = paymentValidator;
        this.userService = userService;
        this.historyService = historyService;
    }

    @GetMapping("/block")
    public String blockBill(@RequestParam(value = "id", required = false) String id){
        if (id == null){
            return "redirect:/main?error";
        }
        billService.blockBill(id);
        return "redirect:/main";
    }

    @GetMapping("/create")
    public String createBillPage(@ModelAttribute("bill") Bill bill){
        return "/CreateBill";
    }

    @PostMapping("/create")
    public String createBill(@ModelAttribute("bill") @Valid Bill bill, BindingResult bindingResult){
    billValidator.validate(bill, bindingResult);
    if (bindingResult.hasErrors()){
        return "/CreateBill";
    }
    billService.saveClear(bill);
    return "redirect:/main";
    }

    @GetMapping("/fund")
    public String fundBIllPage(@RequestParam(value = "id", required = false) String id, Model model){
        if (id == null){
            return "redirect:/main?error";
        }
        model.addAttribute("id", id);
        return "/Fund";
    }

    @PostMapping("/fund/{id}")
    public String fundBIll(@PathVariable(value = "id") String id, @RequestParam("amount") String value){
        if (value.isEmpty()){
            return "redirect:/fund?error";
        }
        billService.fundBill(id, value);
        return "redirect:/main";
    }

    @GetMapping("/payment")
    public String paymentPage(@RequestParam(value = "id", required = false) String id,
                              @ModelAttribute("payment") Payment payment,
                              Model model){
        if (id == null){
            return "redirect:/main?error";
        }
        model.addAttribute("id", id);
        return "/Payment";
    }

    @PostMapping("/payment/{id}")
    public String payment(@PathVariable(value = "id") String id, @ModelAttribute("payment") @Valid Payment payment,
                          BindingResult bindingResult){
        payment.setSenderBillId(id);
        paymentValidator.validate(payment, bindingResult);
        if (bindingResult.hasErrors()){
            return "/Payment";
        }
        Bill senderBill = billService.getBillById(Integer.parseInt(id));
        Bill receiverBill = billService.isExists(userService.getUserByUsername(payment.getReceiverLogin()).get(),
                payment.getReceiverBillName()).get();
        billService.makePayment(senderBill, receiverBill, payment.getAmount());
        historyService.save(senderBill, receiverBill, payment.getAmount(), payment.getName());
        return "redirect:/main";
    }




}
