package ru.chichaev.banking.BankingApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "user")
public class User {

	@Column(name = "username")
	@Length(min = 2, max = 16, message = "Length must be from 3 to 16")
	private String username;

	@Column(name = "password")
	@NotBlank(message = "please input the password")
	private String password;

	@OneToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;

	@Column(name = "is_blocked")
	private boolean isBlocked;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public User(String username, String password, Role role, boolean isBlocked, int id) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.isBlocked = isBlocked;
		this.id = id;
	}

	public User() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean blocked) {
		isBlocked = blocked;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
