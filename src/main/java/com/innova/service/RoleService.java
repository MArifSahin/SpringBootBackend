package com.innova.service;

import com.innova.model.Role;
import com.innova.model.Roles;

import java.util.Optional;

public interface RoleService {
    public Optional<Role> findByRole(Roles roles);
    public Optional<Role> findById(int id);

}
