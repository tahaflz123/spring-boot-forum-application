package com.forum.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
	
	private List<UserResults> users;
	
	private List<PostResults> posts;
	
}
