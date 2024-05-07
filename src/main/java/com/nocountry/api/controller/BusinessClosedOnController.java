package com.nocountry.api.controller;

import com.nocountry.api.dto.business_closed_on.BusinessClosedOnInfoDTO;
import com.nocountry.api.dto.business_closed_on.BusinessClosedOnDTO;
import com.nocountry.api.service.business.BusinessClosedOnService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BookerApp")
public class BusinessClosedOnController {

    @Autowired
    BusinessClosedOnService businessClosedOnService;

    /**
     * Allows to create a date/time when the business will be closed
     *
     * @param businessClosedOnInfoDTO BusinessClosedOnDTO entity
     * @return BusinessClosedOnInfoDTO which is a simplified entity of BusinessClosedOn and contains only necessary
     * information
     */
    @PostMapping("/admin/business_closed_on")
    @Transactional
    public ResponseEntity<BusinessClosedOnDTO> create(@RequestBody BusinessClosedOnInfoDTO businessClosedOnInfoDTO) {
        BusinessClosedOnDTO businessClosedOnDTO = businessClosedOnService.create(businessClosedOnInfoDTO);
        return new ResponseEntity<BusinessClosedOnDTO>(businessClosedOnDTO, HttpStatus.CREATED);
    }

    /**
     * Allows to get all the dates/times when a specific business will be closed on
     *
     * @param businessId ID of the business
     * @return List of BusinessClosedOnInfoDTO which is a simplified entity of BusinessClosedOn and contains only
     * necessary information
     */
    @GetMapping("/business_closed_on/{business_id}")
    public ResponseEntity<List<BusinessClosedOnDTO>> listAllByBusinessId(@PathVariable("business_id") Long businessId) {
        List<BusinessClosedOnDTO> businessClosedOnDTOS = businessClosedOnService.listAllByBusinessId(businessId);
        return new ResponseEntity<List<BusinessClosedOnDTO>>(businessClosedOnDTOS, HttpStatus.OK);
    }

    /**
     * Allows to delete a BusinessClosedOn entity
     *
     * @param id ID of the BusinessClosedOn entity
     * @return No Content (204) HTTP status
     */
    @DeleteMapping("/admin/business_closed_on/{business_closed_on_id}")
    public ResponseEntity delete(@PathVariable("business_closed_on_id") Long id) {
        businessClosedOnService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
