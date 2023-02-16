package ru.chichaev.banking.BankingApp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chichaev.banking.BankingApp.entity.Bill;
import ru.chichaev.banking.BankingApp.entity.History;
import ru.chichaev.banking.BankingApp.entity.User;
import ru.chichaev.banking.BankingApp.repository.HistoryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final BillService billService;

    public HistoryService(HistoryRepository historyRepository, BillService billService) {
        this.historyRepository = historyRepository;
        this.billService = billService;
    }

    @Transactional
    public void save(Bill sender, Bill receiver, String amount, String name){
        LocalDate localDate = LocalDate.now();
        History history = new History(receiver, receiver.getUser(),
                sender, sender.getUser(), Integer.parseInt(amount), localDate, name);
        historyRepository.save(history);
    }

    public ArrayList<String> getIncomingHistory(User receiverUser){
        ArrayList<History> histories = historyRepository.getHistoriesByReceiver(receiverUser);
        ArrayList<String> incomingHistory = new ArrayList<>();
        for (History h: histories){
            incomingHistory.add("To your bill " + h.getReceiverBill().getName() +
                    " from bill " + h.getSenderBill().getName() +
                    " of user " + h.getSender().getUsername() +
                    " was sent " + h.getAmount() +
                    " at " + h.getDate());

        }
        return incomingHistory;
    }

    public ArrayList<String> getOutgoingHistory(User senderUser){
        ArrayList<History> histories = historyRepository.getHistoriesBySender(senderUser);
        ArrayList<String> outgoingHistory = new ArrayList<>();
        for (History h: histories){
            outgoingHistory.add("From your bill " + h.getSenderBill().getName() +
                    " to bill " + h.getReceiverBill().getName() +
                    " of user " + h.getReceiver().getUsername() +
                    " was sent " + h.getAmount() +
                    " at " + h.getDate());
        }
        return outgoingHistory;
    }

}
