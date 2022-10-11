package com.example.MyTweetter.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MyTweetter.data.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findUserByEmail(String email);
	public User findUserByEmailAndPassword(String email, String password);
}
