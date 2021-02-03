package com.logistics.rms.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.rms.dto.RateDTO;
import com.logistics.rms.entity.RateManagement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RateMapper {

    private ObjectMapper objectMapper = new ObjectMapper();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public RateManagement getRateManagement(RateDTO rateDTO) throws Exception{
        RateManagement rateManagement = new RateManagement();
        rateManagement.setAmount(rateDTO.getAmount());
        rateManagement.setDescription(rateDTO.getDescription());
        rateManagement.setEffectiveDate(asLocalDateTime(rateDTO.getEffectiveDate()));
        rateManagement.setExpirationDate(asLocalDateTime(rateDTO.getExpirationDate()));
        return rateManagement;
    }

    public RateDTO getRateDTO(RateManagement rateManagement){
        RateDTO rateDTO = new RateDTO();
        rateDTO.setAmount(rateManagement.getAmount());
        rateDTO.setDescription(rateManagement.getDescription());
        rateDTO.setId(rateManagement.getId());
        rateDTO.setEffectiveDate(rateManagement.getEffectiveDate().format(dateFormatter));
        rateDTO.setExpirationDate(rateManagement.getExpirationDate().format(dateFormatter));
        return rateDTO;
    }

    private ObjectMapper getObjectMapper(){
       return objectMapper;
    }

    public LocalDateTime asLocalDateTime(String date) {
        return LocalDateTime.parse(date, dateFormatter);
    }
}
