package ru.chichaev.banking.BankingApp.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chichaev.banking.BankingApp.entity.Bill;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.repository.BillRepository;
import ru.chichaev.banking.BankingApp.security.UserDetails;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Transactional
    public void blockBill(String id) {
            Bill bill = billRepository.getBillById(Integer.parseInt(id));
            bill.setBlocked(true);
            billRepository.save(bill);
    }


    public Boolean isExistsAtUser(Bill bill){
        User user = getUser();
        Optional<Bill> billOptional = billRepository.getBillByNameAndUser(bill.getName(), user);
        if (billOptional.isPresent()){
                return true;
        }
        return false;
    }

    @Transactional()
    public void saveClear(Bill bill){
        User user = getUser();
        bill.setBalance(0);
        bill.setBlocked(false);
        bill.setUser(user);
        billRepository.save(bill);
    }

    @Transactional
    public void save(Bill bill){
        billRepository.save(bill);
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }

    public Bill getBillById(int id) {
        return billRepository.getBillById(id);
    }

    @Transactional
    public void fundBill(String id, String value) {
        Bill bill = billRepository.getBillById(Integer.parseInt(id));
        bill.setBalance(bill.getBalance() + Integer.parseInt(value));
        billRepository.save(bill);
    }

    public Optional<Bill> isExists(User user, String billName){
        return billRepository.getBillByNameAndUser(billName , user);
    }

    @Transactional
    public void makePayment(Bill senderBill, Bill receiverBill, String amount){
        senderBill.setBalance(senderBill.getBalance() - Integer.parseInt(amount));
        save(senderBill);
        receiverBill.setBalance(receiverBill.getBalance() + Integer.parseInt(amount));
        save(receiverBill);

    }

}
