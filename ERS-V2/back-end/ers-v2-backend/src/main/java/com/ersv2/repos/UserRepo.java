package com.ersv2.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ersv2.models.User;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> getByEmail(String email);
}
