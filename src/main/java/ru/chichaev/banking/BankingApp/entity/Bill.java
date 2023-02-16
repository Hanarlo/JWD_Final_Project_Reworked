package ru.chichaev.banking.BankingApp.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "bill")
public class Bill {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;

		@Column(name = "balance")
		@Min(0)
		private int balance;

		@Column(name = "name")
		@NotNull
		@Length(min = 3, max = 30)
		private String name;

		@Column(name = "is_blocked")
		private boolean isBlocked;

		@ManyToOne
		@JoinColumn(name = "user_id", referencedColumnName = "id")
		private User user;
		
		public Bill() {}
		
		public Bill(String name) {
			super();
			this.name = name;
		}
		
		
		public Bill(int id, boolean isBlocked,int balance, User user, String name) {
			super();
			this.id = id;
			this.balance = balance;
			this.name = name;
			this.isBlocked = isBlocked;
			this.user = user;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getBalance() {
			return balance;
		}

		public void setBalance(int balance) {
			this.balance = balance;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isBlocked() {
			return isBlocked;
		}

		public void setBlocked(boolean isBlocked) {
			this.isBlocked = isBlocked;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

	@Override
	public String toString() {
		return "Bill{" +
				"id=" + id +
				", balance=" + balance +
				", name='" + name + '\'' +
				", isBlocked=" + isBlocked +
				", user=" + user +
				'}';
	}
}
