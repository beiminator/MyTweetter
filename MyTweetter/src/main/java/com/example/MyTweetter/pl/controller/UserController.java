package com.example.MyTweetter.pl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyTweetter.bll.service.UserService;
import com.example.MyTweetter.data.model.User;
import com.example.MyTweetter.pl.model.ResponseObject;

@RestController
@RequestMapping("user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("")
	public ResponseEntity<ResponseObject> saveUser(@RequestBody User _user) {
		ResponseObject result = null;
		List<String> errors = new ArrayList<String>();
		if (!_user.isValid()) {
			errors.add("Invalid email or empty password");
			result = ResponseObject.ko(errors);
		} else {
			User user = userService.getByEmail(_user.getEmail());
			if (user != null) {
				errors.add("User alredy present");
				result = ResponseObject.ko(errors);
			} else {
				userService.saveUser(_user);
				result = ResponseObject.ok();
			}
			
		}
		
		return ResponseEntity.ok(result);
	}
	@PutMapping("")
	public ResponseEntity<ResponseObject> editUser(@RequestBody User _user) {
		ResponseObject result = null;
		List<String> errors = new ArrayList<String>();
		if (_user.getEmail() != null) {
			errors.add("Email not changable");
			result = ResponseObject.ko(errors);
		} else {
			User user = userService.getById(_user.getIdUser()).orElse(null);
			if (user == null) {
				errors.add("User not found");
				result = ResponseObject.ko(errors);
			} else {
				changeUser(user, _user);
				if (!user.isValid()) {
					errors.add("Empty password");
					result = ResponseObject.ko(errors);					
				} else {
					userService.saveUser(user);
					result = ResponseObject.ok(user.clone());					
				}
			}
			
		}
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getUser(@PathVariable("id") Long id) {
		ResponseObject result = null;
		List<String> errors = new ArrayList<String>();
		
		User user = userService.getById(id).orElse(null);
		if (user == null) {
			errors.add("User not found");
			result = ResponseObject.ko(errors);
		} else {
			result = ResponseObject.ok(user.clone());					
		}
		
		return ResponseEntity.ok(result);
	}
	
	private void changeUser(User orig, User changes) {
		if (changes.getName() != null) {
			orig.setName(changes.getName());
		}
		if (changes.getSurname() != null) {
			orig.setSurname(changes.getSurname());
		}
		if (changes.getPassword() != null) {
			orig.setPassword(changes.getPassword());
		}
		return;
	}
}
