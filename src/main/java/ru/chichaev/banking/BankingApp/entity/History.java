package ru.chichaev.banking.BankingApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "history")

public class History {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "receiver_bill_id", referencedColumnName = "id")
		private Bill receiverBill;

	@ManyToOne
	@JoinColumn(name = "receiver_id", referencedColumnName = "id")
	private User receiver;

	@ManyToOne
	@JoinColumn(name = "sender_bill_id", referencedColumnName = "id")
		private Bill senderBill;

	@ManyToOne
	@JoinColumn(name = "sender_id", referencedColumnName = "id")
		private User sender;

	@Column(name = "amount")
	@Min(0)
		private int amount;

	@Column(name = "date")
		private LocalDate date;


	public History(int id, Bill receiverBillID, User receiverID, Bill senderBillID,
				   User senderID,
				   int amount,
				   LocalDate date,
				   String name) {
		this.id = id;
		this.receiverBill = receiverBillID;
		this.receiver = receiverID;
		this.senderBill = senderBillID;
		this.sender = senderID;
		this.amount = amount;
		this.date = date;
		this.name = name;
	}

	public History(Bill receiverBill, User receiver, Bill senderBill, User sender, int amount, LocalDate date, String name) {
		this.receiverBill = receiverBill;
		this.receiver = receiver;
		this.senderBill = senderBill;
		this.sender = sender;
		this.amount = amount;
		this.date = date;
		this.name = name;
	}

	public History() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bill getReceiverBill() {
		return receiverBill;
	}

	public void setReceiverBill(Bill receiverBill) {
		this.receiverBill = receiverBill;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Bill getSenderBill() {
		return senderBill;
	}

	public void setSenderBill(Bill senderBill) {
		this.senderBill = senderBill;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
