package com.logistics.rms.service;

import com.logistics.rms.dto.RateDTO;
import com.logistics.rms.dto.SurCharge;
import com.logistics.rms.entity.RateManagement;
import com.logistics.rms.mapper.RateMapper;
import com.logistics.rms.repository.RMSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RMSService {

    @Autowired
    private RMSRepository rmsRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RateMapper rateMapper;

    public RateManagement getRateManagementById(Long id){
        RateManagement rateManagement = null;
        Optional<RateManagement> optional = rmsRepository.findById(id);
        if(optional.isPresent())
            rateManagement = optional.get();
        SurCharge surCharge = getSurcharge();
        if(rateManagement!=null && surCharge !=null
                && surCharge.getSurchargeDescription().toUpperCase().equals("VAT")){
            //TODO - NOT SURE ABOUT THE FORMULA TO APPLY ON SURCHARGE AMOUNT
            Integer amount = rateManagement.getAmount()+surCharge.getSurchargeRate();
            rateManagement.setAmount(amount);
        }
        return rateManagement;
    }

    public RateDTO saveRateManagement(RateDTO rateDTO) throws Exception{
        RateManagement rateManagement = rateMapper.getRateManagement(rateDTO);
        rateManagement = rmsRepository.save(rateManagement);
        return rateMapper.getRateDTO(rateManagement);
    }

    public RateDTO updateRateManagement(Long id, RateDTO rateDTO){
        RateManagement rateManagementFromDB = getRateManagementById(id);
        transformData(rateManagementFromDB, rateDTO);
        rateManagementFromDB = rmsRepository.save(rateManagementFromDB);
        return (rateManagementFromDB!=null)?rateMapper.getRateDTO(rateManagementFromDB):null;
    }

    public void deleteRateManagement(Long id){
        RateManagement rateManagement = getRateManagementById(id);
        if(rateManagement!=null)
            rmsRepository.delete(rateManagement);
    }

    private void transformData(RateManagement rateManagementFromDB, RateDTO rateDTO) {
        if(rateManagementFromDB !=null & rateDTO != null){
            rateManagementFromDB.setEffectiveDate(rateMapper.asLocalDateTime(rateDTO.getEffectiveDate()));
            rateManagementFromDB.setDescription(rateDTO.getDescription());
            rateManagementFromDB.setExpirationDate(rateMapper.asLocalDateTime(rateDTO.getExpirationDate()));
            rateManagementFromDB.setAmount(rateDTO.getAmount());
        }
    }

    private SurCharge getSurcharge(){
        Map<String,String> variables = new HashMap<>();
        variables.put("content-type","application/json");
        restTemplate.setDefaultUriVariables(variables);
        return restTemplate.getForObject("https://surcharge.free.beeceptor.com/surcharge",
                        SurCharge.class, variables);
    }


}
