package com.nocountry.api.controller;

import com.nocountry.api.dto.business.BusinessDTO;
import com.nocountry.api.service.business.BusinessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BookerApp")
public class BusinessController {

    @Autowired
    BusinessServiceImpl businessServiceImpl;

    /**
     * Allows to get a business by its id
     *
     * @param id The id of the Business entity
     * @return BusinessDTO entity which is a simplified datatype of Business
     */
    @GetMapping("/businesses/{id}")
    public ResponseEntity<BusinessDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<BusinessDTO>(businessServiceImpl.getById(id), HttpStatus.OK);
    }

    /**
     * Allows to get all the businesses
     *
     * @return List of BusinessDTO entity which is a simplified datatype of Business
     */
    @GetMapping("/businesses")
    public ResponseEntity<List<BusinessDTO>> listAll() {
        return new ResponseEntity<List<BusinessDTO>>(businessServiceImpl.listAll(), HttpStatus.OK);
    }
}
