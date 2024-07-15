package com.bciexercise.exercise.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RespUserDTO extends ResponseDTO {
		
	private String name;
	private String email;
	private String password;
	private List<RespPhoneDTO> phones;
	
	@Builder
	public RespUserDTO(String id, String created, String lastLogin, String token, Boolean isActive,
			String name, String email,String password, List<RespPhoneDTO> phones) {
		
		super(id, created, lastLogin, token, isActive);
		
		this.name = name;
		this.email = email;
		this.created = created;
		this.password = password;
		this.phones = phones;
	}

}
