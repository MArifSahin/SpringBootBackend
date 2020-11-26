package com.innova.service;

import com.innova.model.Role;
import com.innova.model.Roles;
import com.innova.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<Role> findByRole(Roles roles) {
        return roleRepository.findByRole(roles);
    }

    @Override
    public Optional<Role> findById(int id) {
        return roleRepository.findById(id);
    }
}
