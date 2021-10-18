package com.forum.entity;

import java.util.Date;

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
@Table(name = "report")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {
	
	@Id
	@SequenceGenerator(name = "report_sequence",allocationSize = 1)
	@GeneratedValue(generator = "report_sequence",strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long reporterUserId;
	
	private Long postId;
	
	private Long commentId;
	
	private Long userId;
	
	private Date date;

}
