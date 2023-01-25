package com.ersv2.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ersv2.models.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> getByEmail(String email);
	Boolean existsByEmail(String email);
}
