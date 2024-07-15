package com.bciexercise.exercise.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bciexercise.exercise.dto.request.ReqPhoneDTO;
import com.bciexercise.exercise.dto.request.ReqUserDTO;
import com.bciexercise.exercise.dto.response.RespPhoneDTO;
import com.bciexercise.exercise.dto.response.RespUserDTO;
import com.bciexercise.exercise.model.Phone;
import com.bciexercise.exercise.model.User;

public class TestUtils {
	
	public static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI0NmY0MTg4Yi0yOWY5LTQ0MWItYjRlZC04MTY3NGI3ZDE0MDEiLCJzdWIiOiJtYXJpYW5hQGVtYWlsLmNvbSIsImlhdCI6MTcyMDkzMTQ0MX0._r1vKwX6HK6RXxGkgFroM4PiPdETE0775pZlWfDXAwbqxeldu7FFCo8mcKvMf9htNVzxHhmCLNrInIkAt1x4-g";
	public static final String NAME = "Mariana";
	public static final String EMAIL = "mariana@email.com";
	public static final String PWD = "Test12in";
	public static final Long PHONE_NUM = 123456789L;
	public static final int CITY_CODE = 261;
	public static final String COUNTRY_CODE = "54";
	public static final UUID id = UUID.randomUUID();
			
	public static ReqPhoneDTO getRequestPhoneDTO() {
		return ReqPhoneDTO.builder()
						  .number(PHONE_NUM)
						  .citycode(CITY_CODE)
						  .countrycode(COUNTRY_CODE)
						  .build();
	}
	
	public static ReqUserDTO getRequestUserDTO() {
		return  ReqUserDTO.builder()
						  .name(NAME)
						  .email(EMAIL)
						  .password(PWD)
						  .phones(Arrays.asList(getRequestPhoneDTO()))
						  .build();
	}
		
	public static RespUserDTO getSignUpResponseUserDTO() {
		return RespUserDTO.builder()
				   .id("1")
				   .created(LocalDateTime.now().toString())
				   .lastLogin(LocalDateTime.now().toString())
				   .token(BEARER_TOKEN)
				   .isActive(true)
				   .build();
	}
	
	private static RespPhoneDTO getResponsePhoneDTO() {
		return RespPhoneDTO.builder()
				   .number(PHONE_NUM)
				   .citycode(CITY_CODE)
				   .countrycode(COUNTRY_CODE)
				   .build();
	}
	
	public static RespUserDTO getLoginResponseUserDTO() {
		return RespUserDTO.builder()
				   .id(id.toString())
				   .name(NAME)
				   .email(EMAIL)
				   .password(PWD)
				   .phones(Arrays.asList(getResponsePhoneDTO()))
				   .created(LocalDateTime.now().toString())
				   .lastLogin(LocalDateTime.now().toString())
				   .token(BEARER_TOKEN)
				   .isActive(true)
				   .build();
	}
	
	public static List<Phone> createPhone(List<ReqPhoneDTO> reqPhoneDTO, User user) throws Exception {
		List<Phone> phones = Optional.ofNullable(reqPhoneDTO).orElse(Collections.emptyList()).stream()
				.map(pdto -> Phone.builder()
						.number(pdto.getNumber())
						.citycode(pdto.getCitycode())
						.countrycode(pdto.getCountrycode())
						.user(user)
						.build())
				.collect(Collectors.toList());
		
		return phones;
		
	}
	
	public static User createUser(ReqUserDTO reqUserDTO) throws Exception {
		User user = User.builder()
				   .id(id)
				   .name(reqUserDTO.getName())
				   .email(reqUserDTO.getEmail())
				   .password(EncriptDecrypt.encrypt(reqUserDTO.getPassword()))
				   .phones(Collections.emptyList())
				   .created(LocalDateTime.now())
				   .lastLogin(LocalDateTime.now())
				   .token(JwtTokensManagement.generateJwtToken(reqUserDTO.getEmail()))
				   .isActive(true)
				   .build();
		
		List<Phone> phones = createPhone(reqUserDTO.getPhones(), user);
		
		if (!phones.isEmpty())
			user.setPhones(phones);
		
		return user;
		
	}
	
	public static User createLoggedUser() throws Exception {
		User user = User.builder()
				   .id(id)
				   .name(NAME)
				   .email(EMAIL)
				   .password(EncriptDecrypt.encrypt(PWD))
				   .phones(Collections.emptyList())
				   .created(LocalDateTime.now())
				   .lastLogin(LocalDateTime.now())
				   .token(TestUtils.BEARER_TOKEN)
				   .isActive(true)
				   .build();
		
		return user;
		
	}
	
	
	
}
