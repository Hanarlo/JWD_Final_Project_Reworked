package ru.chichaev.banking.BankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chichaev.banking.BankingApp.entity.Bill;
import ru.chichaev.banking.BankingApp.entity.User;

import java.util.ArrayList;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    ArrayList<Bill> getBillsByUser(User user);
    Bill getBillById(int id);

    Optional<Bill> getBillByNameAndUser(String name, User user);

}
