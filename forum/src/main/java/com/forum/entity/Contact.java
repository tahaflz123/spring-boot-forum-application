package com.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact_infos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

	@Id
	@SequenceGenerator(name = "contact_sequence",allocationSize = 1)
	@GeneratedValue(generator = "contact_sequence" ,strategy = GenerationType.SEQUENCE)
	@Column(unique = true)
	private Long id;
	
	private String email;

	@Lob
	private String message;
	
}
