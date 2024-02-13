package com.example.hibernate.converter;

import com.example.hibernate.model.BirthDate;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Date;

@Converter
public class BirthDateConverter implements AttributeConverter<BirthDate, Date> {

    @Override
    public Date convertToDatabaseColumn(BirthDate attribute) {
        return Date.valueOf(attribute.getDate());
    }

    @Override
    public BirthDate convertToEntityAttribute(Date dbData) {
        BirthDate birthDate = new BirthDate();
        birthDate.setDate(dbData.toLocalDate());
        return birthDate;
    }
}
