package com.example.MyTweetter.bll.service;

import java.util.Optional;

import com.example.MyTweetter.data.model.User;

public interface UserService {
	public void saveUser(User user);
	public Optional<User> getById(Long id);
	public User getByEmail(String email);
	public User getByEmailAndPassword(String email, String password);
}
