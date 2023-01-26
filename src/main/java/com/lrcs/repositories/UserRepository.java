package com.lrcs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrcs.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
