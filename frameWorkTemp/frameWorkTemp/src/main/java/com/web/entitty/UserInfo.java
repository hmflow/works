package com.web.entitty;
 

import com.web.config.converter.PasswordConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; 
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "MEMBER_INFO")
public class UserInfo {
	@Id
	@Column(name="seq", length=20) 
	@GeneratedValue(strategy = GenerationType.SEQUENCE) 
	private int seq;
	 
	@Column(name="id", length=50)
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private String id;
	 
	@Convert(converter = PasswordConverter.class)
	@Column(name="pass", length=250) 
	private String pass;
}  
  