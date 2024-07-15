package com.bciexercise.exercise.service;

import com.bciexercise.exercise.dto.request.ReqUserDTO;
import com.bciexercise.exercise.dto.response.RespUserDTO;
import com.bciexercise.exercise.exception.UserAlreadyExistsException;
import com.bciexercise.exercise.exception.UserNotFoundException;

public interface UserService {

	public RespUserDTO signUp(final ReqUserDTO reqUserDTO) throws UserAlreadyExistsException, Exception;
	
	public RespUserDTO login(String token) throws UserNotFoundException, Exception;
	
}
