package com.lrcs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrcs.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
