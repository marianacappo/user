package com.bciexercise.exercise.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bciexercise.exercise.dto.request.ReqUserDTO;
import com.bciexercise.exercise.dto.response.RespPhoneDTO;
import com.bciexercise.exercise.dto.response.RespUserDTO;
import com.bciexercise.exercise.enumeration.Errors;
import com.bciexercise.exercise.exception.GlobalException;
import com.bciexercise.exercise.exception.UserAlreadyExistsException;
import com.bciexercise.exercise.exception.UserNotFoundException;
import com.bciexercise.exercise.model.Phone;
import com.bciexercise.exercise.model.User;
import com.bciexercise.exercise.repository.UserRepository;
import com.bciexercise.exercise.service.UserService;
import com.bciexercise.exercise.util.EncriptDecrypt;
import com.bciexercise.exercise.util.JwtTokensManagement;

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LogManager.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public RespUserDTO signUp(final ReqUserDTO reqUserDTO) throws UserAlreadyExistsException, GlobalException {

		if (userRepository.findByEmail(reqUserDTO.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException(Errors.EMAILALREADYEXISTS.getDetail(),
					Errors.EMAILALREADYEXISTS.getCode());
		}
		
		try {
		
	    User user = User.builder()
			.name(reqUserDTO.getName())
			.email(reqUserDTO.getEmail())
			.password(EncriptDecrypt.encrypt(reqUserDTO.getPassword()))
			.phones(Collections.emptyList())
			.created(LocalDateTime.now())
			.lastLogin(LocalDateTime.now())
			.isActive(true)
			.token(JwtTokensManagement.generateJwtToken(reqUserDTO.getEmail()))
			.build();

		List<Phone> phones = Optional.ofNullable(reqUserDTO.getPhones()).orElse(Collections.emptyList()).stream()
				.map(pdto -> Phone.builder()
						.number(pdto.getNumber())
						.citycode(pdto.getCitycode())
						.countrycode(pdto.getCountrycode())
						.user(user)
						.build())
				.collect(Collectors.toList());

		if (!phones.isEmpty())
			user.setPhones(phones);

		User savedUser = userRepository.save(user);

		return RespUserDTO.builder()
							.id(savedUser.getId().toString())
							.created(savedUser.getCreated().toString())
							.lastLogin(savedUser.getLastLogin().toString())
							.token(savedUser.getToken())
							.isActive(savedUser.isActive())
							.build();
		}catch(Exception e) {
			throw new GlobalException(Errors.GENERALEXCEPTION.getDetail(), Errors.GENERALEXCEPTION.getCode());
		}
	}

	@Override
	public RespUserDTO login(String token) throws UserNotFoundException, Exception {
		String email = JwtTokensManagement.getUsernameFromJwtToken(token);
		
		User user = userRepository.findByEmail(email).orElseThrow(()-> 
		               					new UserNotFoundException(Errors.USERNOTFOUNDEXCEPTION.getDetail(),
		                        		Errors.USERNOTFOUNDEXCEPTION.getCode()));
		
		user.setLastLogin(LocalDateTime.now());
		user.setToken(JwtTokensManagement.generateJwtToken(user.getEmail()));
		userRepository.save(user);
		
		List<RespPhoneDTO> respPhoneDTOs = Optional.ofNullable(user.getPhones()).orElse(Collections.emptyList()).stream()
											.map(p -> RespPhoneDTO.builder()
													.number(p.getNumber())
													.citycode(p.getCitycode())
													.countrycode(p.getCountrycode())
													.build())
											.collect(Collectors.toList());
		
		RespUserDTO resp = RespUserDTO.builder()
				.id(user.getId().toString())
				.created(user.getCreated().toString())
				.lastLogin(user.getLastLogin().toString())
				.token(user.getToken())
				.isActive(user.isActive())
				.name(user.getName())
				.email(user.getEmail())
				.password(EncriptDecrypt.decrypt(user.getPassword()))
				.phones(respPhoneDTOs)
				.build();
		return resp;
	}

}
