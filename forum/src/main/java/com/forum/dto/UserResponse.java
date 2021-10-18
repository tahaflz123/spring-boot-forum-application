package com.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

	private Long userId;
	
	private String username;
	
	private Integer z_point;
	
	private Integer postCount;
	
}
