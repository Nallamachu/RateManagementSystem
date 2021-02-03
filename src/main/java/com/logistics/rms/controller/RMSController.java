package com.logistics.rms.controller;

import com.logistics.rms.dto.RateDTO;
import com.logistics.rms.entity.RateManagement;
import com.logistics.rms.service.RMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/api/rms")
public class RMSController {

    @Autowired
    private RMSService rmsService;

    @GetMapping(path = "/byId/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RateManagement> getRateManagementById(@PathVariable("id") Long id){
        if(id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(rmsService.getRateManagementById(id),HttpStatus.OK);
    }

    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RateDTO> saveRateManagement(@RequestBody RateDTO rate) throws Exception {
        if(rate==null
                && (rate.getEffectiveDate()==null || rate.getExpirationDate()==null || rate.getAmount()<0))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        rate = rmsService.saveRateManagement(rate);
        return (rate != null)
                ?new ResponseEntity<>(rate,HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PutMapping(path = "/update/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RateDTO> updateRateManagement(@PathVariable("id") Long id
            ,@RequestBody RateDTO rateDTO){
        if(id == null || rateDTO == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        rateDTO = rmsService.updateRateManagement(id,rateDTO);
        return (rateDTO!=null)
                ?new ResponseEntity<>(rateDTO,HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteRateManagement(@PathVariable("id") Long id){
        if(id == null)
            return new ResponseEntity<>("ID should not be null",HttpStatus.BAD_REQUEST);
        rmsService.deleteRateManagement(id);
        return new ResponseEntity<>("Succesfully deleted with ID "+id, HttpStatus.OK);
    }

    @GetMapping("/")
    public String home(Principal user) {
        return "Welcome, "+ user.getName() + "!";
    }
}
