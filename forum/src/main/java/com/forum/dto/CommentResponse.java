package com.forum.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

	private Long id;
	
	private Long post_id;
	
	private Integer likeCount;
	
	private Long userId;
	
	private int currentPage;
	
	private int pageSize;
	
	private Date commentDate;
	
	private String text;
	
	private String username;
	
	private boolean liked = false;
	
}