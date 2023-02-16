package ru.chichaev.banking.BankingApp.service;

import org.springframework.stereotype.Service;
import ru.chichaev.banking.BankingApp.entity.Role;
import ru.chichaev.banking.BankingApp.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRole(int roleId){
        return roleRepository.findById(roleId);
    }
}
