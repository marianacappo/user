package com.bciexercise.exercise.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bciexercise.exercise.dto.request.ReqUserDTO;
import com.bciexercise.exercise.dto.response.RespUserDTO;
import com.bciexercise.exercise.exception.GlobalException;
import com.bciexercise.exercise.exception.UserAlreadyExistsException;
import com.bciexercise.exercise.exception.UserNotFoundException;
import com.bciexercise.exercise.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserServiceImpl userService;

	@Autowired
	public UserController(final UserServiceImpl userService) {
		this.userService = userService;
	}

	@PostMapping("/sign-up")
	public ResponseEntity<RespUserDTO> signUp(@RequestBody @Valid ReqUserDTO reqUserDTO)
			throws GlobalException, UserAlreadyExistsException, MethodArgumentNotValidException, Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(reqUserDTO));
	}

	@GetMapping("/login")
	public ResponseEntity<RespUserDTO> login(@RequestHeader("Authorization") String token) 
			throws GlobalException, UserNotFoundException, Exception {
		return ResponseEntity.status(HttpStatus.OK).body(userService.login(token));
	}

}
