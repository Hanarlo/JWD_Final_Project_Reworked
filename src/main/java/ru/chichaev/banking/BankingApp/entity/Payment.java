package ru.chichaev.banking.BankingApp.entity;

public class Payment {

    private String receiverLogin;
    private String receiverBillName;
    private String amount;

    private String name;

    private String SenderBillId;

    public String getReceiverLogin() {
        return receiverLogin;
    }

    public void setReceiverLogin(String receiverLogin) {
        this.receiverLogin = receiverLogin;
    }

    public String getSenderBillId() {
        return SenderBillId;
    }

    public void setSenderBillId(String senderBillId) {
        SenderBillId = senderBillId;
    }

    public String getReceiverBillName() {
        return receiverBillName;
    }

    public void setReceiverBillName(String receiverBillName) {
        this.receiverBillName = receiverBillName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
