package com.web.config.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PasswordConverter implements AttributeConverter<String, String>{

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return encode(attribute) ;
 
	}


	@Override
	public String convertToEntityAttribute(String dbData) { 
		return dbData;
	}
	

	private String encode(String attribute) { 
		return attribute;
	}
}
