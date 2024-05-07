package com.nocountry.api.controller;

import com.nocountry.api.dto.business_schedule.BusinessScheduleInfoDTO;
import com.nocountry.api.dto.business_schedule.BusinessScheduleDTO;
import com.nocountry.api.service.business.BusinessScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BookerApp")
public class BusinessScheduleController {

    @Autowired
    BusinessScheduleService businessScheduleService;

    /**
     * Allows to create a business schedule for a specific business
     *
     * @param businessScheduleInfoDTO BusinessScheduleDTO entity
     * @return BusinessScheduleInfoDTO datatype which contains only the necessary information about the business schedule.
     */
    @PostMapping("/admin/business_schedule")
    @Transactional
    public ResponseEntity<BusinessScheduleDTO> create(@RequestBody BusinessScheduleInfoDTO businessScheduleInfoDTO) {
        BusinessScheduleDTO businessScheduleDTO = businessScheduleService.create(businessScheduleInfoDTO);
        return new ResponseEntity<BusinessScheduleDTO>(businessScheduleDTO, HttpStatus.CREATED);
    }

    /**
     * Allows to get a list of the schedules of a business by its ID
     *
     * @param businessId ID of the business
     * @return List of BusinessScheduleInfoDTO datatype which contains only the necessary information about the
     * business schedule.
     */
    @GetMapping("/admin/business_schedule/{business_id}")
    public ResponseEntity<List<BusinessScheduleDTO>> listAllByBusinessId(@PathVariable("business_id") Long businessId) {
        List<BusinessScheduleDTO> businessScheduleDTOList = businessScheduleService.listAllByBusinessId(businessId);
        return new ResponseEntity<List<BusinessScheduleDTO>>(businessScheduleDTOList, HttpStatus.OK);
    }

    /**
     * Allows to delete a BusinessSchedule entity by its ID
     *
     * @param businessId ID of the business
     * @return No Content (204) HTTP status
     */
    @DeleteMapping("/admin/business_schedule/{business_id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("business_id") Long businessId) {
        businessScheduleService.deleteById(businessId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
