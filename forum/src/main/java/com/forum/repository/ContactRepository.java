package com.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forum.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

}
