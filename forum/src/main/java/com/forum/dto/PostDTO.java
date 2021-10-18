package com.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

	private String header;
	
	private Long categoryId;
	
	private String text;
}
