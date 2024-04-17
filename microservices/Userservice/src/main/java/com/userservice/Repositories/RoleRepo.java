package com.userservice.Repositories;

import com.userservice.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    public Role findByRole(String role);
}
