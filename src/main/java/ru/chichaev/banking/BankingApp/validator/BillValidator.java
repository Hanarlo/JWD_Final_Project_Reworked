package ru.chichaev.banking.BankingApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chichaev.banking.BankingApp.entity.Bill;
import ru.chichaev.banking.BankingApp.service.BillService;

@Component
public class BillValidator implements Validator {

    private final BillService billService;

    public BillValidator(BillService billService) {
        this.billService = billService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Bill.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Bill bill = (Bill) target;
        if (billService.isExistsAtUser(bill)){
            errors.rejectValue("name", "", "Bill with this name is already exists!");
        }
        return;

    }
}
