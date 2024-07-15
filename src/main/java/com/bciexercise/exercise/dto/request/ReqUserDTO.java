package com.bciexercise.exercise.dto.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReqUserDTO {

	private String name;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Pattern(regexp = "^(?=.*[A-Z])(?=(?:[^0-9]*[0-9]){2}[^0-9]*$)(?!.*[A-Z].*[A-Z])[a-zA-Z0-9]{8,12}$")
	private String password;
	private List<ReqPhoneDTO> phones;
	
}
