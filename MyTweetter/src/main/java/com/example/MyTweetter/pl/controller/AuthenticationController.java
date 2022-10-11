package com.example.MyTweetter.pl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyTweetter.bll.service.UserService;
import com.example.MyTweetter.data.model.User;
import com.example.MyTweetter.pl.model.ResponseObject;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	private final UserService userService;

	public AuthenticationController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("login")
	public ResponseEntity<ResponseObject> login(@RequestBody User _user) {
		ResponseObject result = null;
		List<String> errors = new ArrayList<String>();
		if (_user.getEmail() == null || _user.getPassword() == null ) {
			errors.add("User without either email or password");
			result = ResponseObject.ko(errors);
		} else {
			User user = userService.getByEmailAndPassword(_user.getEmail(), _user.getPassword());
			if (user == null) {
				errors.add("User not found");
				result = ResponseObject.ko(errors);
			} else {
				user.setPassword(null);
				result = ResponseObject.ok(user.clone());
			}
		}
		
		return ResponseEntity.ok(result);
	}
}
