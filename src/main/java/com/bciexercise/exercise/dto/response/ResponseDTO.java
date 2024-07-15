package com.bciexercise.exercise.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ResponseDTO {

	protected String id;
	protected String created;
	protected String lastLogin;
	protected String token;
	protected Boolean isActive; 
}
