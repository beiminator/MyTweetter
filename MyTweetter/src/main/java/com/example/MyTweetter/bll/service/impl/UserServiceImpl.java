package com.example.MyTweetter.bll.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.MyTweetter.bll.service.UserService;
import com.example.MyTweetter.data.model.User;
import com.example.MyTweetter.data.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public void saveUser(User user) {
		userRepository.save(user);
		return;
	}

	@Override
	public Optional<User> getById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public User getByEmail (String email) {
		return userRepository.findUserByEmail(email);
	}
	
	@Override
	public User getByEmailAndPassword (String email, String password) {
		return userRepository.findUserByEmailAndPassword(email, password);
	}
	
}
