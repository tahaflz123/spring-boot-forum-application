package com.forum.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

	@Id
	@SequenceGenerator(name = "comment_sequence",allocationSize = 1)
	@GeneratedValue(generator = "comment_sequence",strategy = GenerationType.SEQUENCE)
	@Column(unique = true)
	private Long id;
	
	@Lob
	private String text;
	
	@Builder.Default
	private Integer likeCount = 0;
	
	private Date commentDate;
	
	@Builder.Default
	private Boolean deleted = false;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "post_id") 
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
}
