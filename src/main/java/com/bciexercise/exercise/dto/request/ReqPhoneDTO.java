package com.bciexercise.exercise.dto.request;

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
public class ReqPhoneDTO {

	private Long number;
	private int citycode;
	private String countrycode;

}
