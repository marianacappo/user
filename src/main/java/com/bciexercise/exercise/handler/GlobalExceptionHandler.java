package com.bciexercise.exercise.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bciexercise.exercise.dto.response.ErrorDetailDTO;
import com.bciexercise.exercise.dto.response.ErrorResponseDTO;
import com.bciexercise.exercise.enumeration.Errors;
import com.bciexercise.exercise.exception.UserAlreadyExistsException;
import com.bciexercise.exercise.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserAlreadyExistsException.class)
	protected ResponseEntity<ErrorResponseDTO> onUserAlreadyExistsException(UserAlreadyExistsException e,
			Locale locale) {
		return ResponseEntity.badRequest().body(this.errorBuilder(e.getCode(), e.getMessage()));
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<ErrorResponseDTO> onUserNotFoundException(UserNotFoundException e,
			Locale locale) {
		return ResponseEntity.badRequest().body(this.errorBuilder(e.getCode(), e.getMessage()));
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	protected ResponseEntity<ErrorResponseDTO> onMethodArgumentNotValidException(MethodArgumentNotValidException e,
			Locale locale) {
		List<String> argumentError = new ArrayList<>();

		e.getFieldErrors().forEach(error -> {
			argumentError.add(error.getField());
			if(!Objects.isNull(error.getRejectedValue()))
				argumentError.add(error.getRejectedValue().toString());
			argumentError.add(error.getDefaultMessage());
		});

		return ResponseEntity.badRequest().body(this.errorBuilder(Errors.ARGUMENTNOTVALIDEXCEPTION.getCode(), 
				Errors.ARGUMENTNOTVALIDEXCEPTION.getDetail() + ": " + argumentError.toString()));
	}
	
	@ExceptionHandler({ Exception.class })
	protected ResponseEntity<ErrorResponseDTO> handleException(Exception e, Locale locale) {
		e.printStackTrace();
		return ResponseEntity.badRequest().body(this.errorBuilder(Errors.GENERALEXCEPTION.getCode(), 
				Errors.GENERALEXCEPTION.getDetail() + ": " + e.getMessage()));
	}
	
	private ErrorResponseDTO errorBuilder(final int code, final String message) {
		
		ErrorDetailDTO errorDetail = ErrorDetailDTO.builder()
                .timestamp(LocalDateTime.now())
                .codigo(code)
                .detail(message)
                .build();

        return ErrorResponseDTO.builder()
                .error(Collections.singletonList(errorDetail))
                .build();
	}
}
