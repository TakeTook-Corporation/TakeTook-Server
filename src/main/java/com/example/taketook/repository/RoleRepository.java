package com.example.taketook.repository;

import com.example.taketook.entity.Role;
import com.example.taketook.utils.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByRole(RoleEnum role);
}
