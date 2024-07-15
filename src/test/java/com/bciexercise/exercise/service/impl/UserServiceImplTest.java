package com.bciexercise.exercise.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bciexercise.exercise.dto.request.ReqUserDTO;
import com.bciexercise.exercise.dto.response.RespUserDTO;
import com.bciexercise.exercise.enumeration.Errors;
import com.bciexercise.exercise.exception.UserAlreadyExistsException;
import com.bciexercise.exercise.exception.UserNotFoundException;
import com.bciexercise.exercise.model.User;
import com.bciexercise.exercise.repository.UserRepository;
import com.bciexercise.exercise.util.JwtTokensManagement;
import com.bciexercise.exercise.util.TestUtils;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;
	
	@Test
	void testSignUp() throws Exception {
		ReqUserDTO reqUserDTO = TestUtils.getRequestUserDTO();
		User user = TestUtils.createUser(reqUserDTO);
		
		when(userRepository.findByEmail(TestUtils.EMAIL)).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		RespUserDTO response = userService.signUp(reqUserDTO);
		
		assertNotNull(response);
        assertEquals(user.getToken(), response.getToken());
        assertTrue(response.getIsActive());
        
        verify(userRepository, times(1)).findByEmail(TestUtils.EMAIL);
        verify(userRepository, times(1)).save(any(User.class));
		
	}
	
	@Test
	void testSignUp_UserAlreadyExists() {
		ReqUserDTO reqUserDTO = TestUtils.getRequestUserDTO();
		
		when(userRepository.findByEmail(reqUserDTO.getEmail())).thenReturn(Optional.of(new User()));
		
		UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.signUp(reqUserDTO);
        });

        assertEquals(Errors.EMAILALREADYEXISTS.getDetail(), exception.getMessage());
        assertEquals(Errors.EMAILALREADYEXISTS.getCode(), exception.getCode());
	}
		
	@Test
	void testLogin() throws Exception {
			
		User user = TestUtils.createLoggedUser();
		
		try (MockedStatic<JwtTokensManagement> mockedStatic = Mockito.mockStatic(JwtTokensManagement.class)) {
			mockedStatic.when(() -> JwtTokensManagement.getUsernameFromJwtToken(TestUtils.BEARER_TOKEN)).thenReturn(TestUtils.EMAIL);
			mockedStatic.when(() -> JwtTokensManagement.generateJwtToken(TestUtils.EMAIL)).thenReturn(TestUtils.BEARER_TOKEN);
			
			when(userRepository.findByEmail(TestUtils.EMAIL)).thenReturn(Optional.of(user));
	        when(userRepository.save(any(User.class))).thenReturn(user);
	        
	        RespUserDTO response = userService.login(TestUtils.BEARER_TOKEN);
	        
	        assertNotNull(response);
            assertEquals(TestUtils.NAME, response.getName());
            assertEquals(TestUtils.EMAIL, response.getEmail());
            assertEquals(TestUtils.BEARER_TOKEN, response.getToken());

            mockedStatic.verify(() -> JwtTokensManagement.getUsernameFromJwtToken(TestUtils.BEARER_TOKEN));
            mockedStatic.verify(() -> JwtTokensManagement.generateJwtToken(TestUtils.EMAIL));
            verify(userRepository).findByEmail(TestUtils.EMAIL);
            verify(userRepository).save(user);
		} 
       
	}
	
	@Test
    void testLogin_UserNotFound() throws Exception {
		String token = TestUtils.BEARER_TOKEN;
		String email = TestUtils.EMAIL;

		try (MockedStatic<JwtTokensManagement> mockedStatic = Mockito.mockStatic(JwtTokensManagement.class)) {
            mockedStatic.when(() -> JwtTokensManagement.getUsernameFromJwtToken(token)).thenReturn(email);
            mockedStatic.when(() -> JwtTokensManagement.generateJwtToken(email)).thenReturn(any(String.class));

            assertThrows(UserNotFoundException.class, () -> userService.login(token));
        }
    }
}
