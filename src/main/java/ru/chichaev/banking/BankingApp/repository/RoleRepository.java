package ru.chichaev.banking.BankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chichaev.banking.BankingApp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
