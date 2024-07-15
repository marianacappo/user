package com.bciexercise.exercise.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bciexercise.exercise.dto.request.ReqUserDTO;
import com.bciexercise.exercise.dto.response.RespUserDTO;
import com.bciexercise.exercise.enumeration.Errors;
import com.bciexercise.exercise.exception.UserAlreadyExistsException;
import com.bciexercise.exercise.exception.UserNotFoundException;
import com.bciexercise.exercise.service.impl.UserServiceImpl;
import com.bciexercise.exercise.util.TestUtils;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	
	@Mock
	private UserServiceImpl userService;
	
	@InjectMocks
	private UserController userController;
	
	@Test
	void testSignUp() throws Exception {
				
		ReqUserDTO reqUserDTO = TestUtils.getRequestUserDTO();
		
		RespUserDTO respUserDTO = TestUtils.getSignUpResponseUserDTO();
				
		when(userService.signUp(reqUserDTO)).thenReturn(respUserDTO);
		
		ResponseEntity<RespUserDTO> response = userController.signUp(reqUserDTO);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(respUserDTO, response.getBody());
		
		verify(userService).signUp(reqUserDTO);
		
	}
	
	@Test
	void testSignUp_UserAlreadyExists() throws Exception {
		
		ReqUserDTO reqUserDTO = TestUtils.getRequestUserDTO();
		
		when(userService.signUp(reqUserDTO)).thenThrow(new UserAlreadyExistsException(Errors.EMAILALREADYEXISTS.getDetail(), Errors.EMAILALREADYEXISTS.getCode()));
		
		UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
			userController.signUp(reqUserDTO);
		});
		
		assertEquals(Errors.EMAILALREADYEXISTS.getDetail(), exception.getMessage());
        assertEquals(1000, exception.getCode());
        
        verify(userService).signUp(reqUserDTO);
	}
	
	@Test
	void testLogin() throws Exception {
		
		RespUserDTO respUserDTO = TestUtils.getLoginResponseUserDTO();
		
		when(userService.login(TestUtils.BEARER_TOKEN)).thenReturn(respUserDTO);
		
		ResponseEntity<RespUserDTO> response = userController.login(TestUtils.BEARER_TOKEN);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(respUserDTO, response.getBody());
        
        verify(userService).login(TestUtils.BEARER_TOKEN);
	}
	
	@Test
	void testLogin_UserNotFound() throws Exception {

		when(userService.login(TestUtils.BEARER_TOKEN)).thenThrow(new UserNotFoundException(Errors.USERNOTFOUNDEXCEPTION.getDetail(), Errors.USERNOTFOUNDEXCEPTION.getCode()));
		
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
			userController.login(TestUtils.BEARER_TOKEN);
		});
		
		assertEquals(Errors.USERNOTFOUNDEXCEPTION.getDetail(), exception.getMessage());
		assertEquals(3000, exception.getCode());
		
		verify(userService).login(TestUtils.BEARER_TOKEN);
	}
	
	
}
