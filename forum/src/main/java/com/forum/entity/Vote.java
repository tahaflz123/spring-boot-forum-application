package com.forum.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Vote {
	
	@Id
	@SequenceGenerator(name = "vote_sequence",allocationSize = 1)
	@GeneratedValue(generator = "vote_sequence",strategy = GenerationType.SEQUENCE)
	@Column(unique = true)
	private Long id;
	
	private Long userId;
	
	private Long postId;
	
	private Long commentId;

}
