package com.sillycat.authcenter.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class User {

	private Long id;

	@NotBlank
	@Size(min = 0, max = 20)
	private String loginName;

	@NotBlank
	@Size(min = 0, max = 32)
	private String email;

}
