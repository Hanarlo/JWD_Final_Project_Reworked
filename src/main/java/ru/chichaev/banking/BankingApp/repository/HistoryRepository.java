package ru.chichaev.banking.BankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chichaev.banking.BankingApp.entity.History;
import ru.chichaev.banking.BankingApp.entity.User;

import java.util.ArrayList;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    ArrayList<History> getHistoriesByReceiver(User receiver);

    ArrayList<History> getHistoriesBySender(User sender);

}
