package com.umg.demo.jwt.demo_jwt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umg.demo.jwt.demo_jwt.dto.User;
import com.umg.demo.jwt.demo_jwt.security.JWTUtil;


@RestController
public class UserController {


	/*
	 * controllador para generar token.
	 */
	@PostMapping("/user")
	public User login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

		String token = JWTUtil.generarToken(username);
		User user = new User();
		user.setUsername(username);
		user.setToken(token);
		return user;

	}

	
}