package com.forum.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

	private Long postId;
	
	private Long userId;
	
	private Integer likeCount;
	
	private String header;
	
	private String text;
	
	private String username;
	
	private Date postDate;
	
	private int pageSize;
	
	private int currentPage;
	
	private boolean liked = false;
	
}
