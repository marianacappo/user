package com.bciexercise.exercise.dto.response;

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
public class RespPhoneDTO {

	private Long number;
	private int citycode;
	private String countrycode;

}
