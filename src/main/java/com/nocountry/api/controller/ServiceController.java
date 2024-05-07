package com.nocountry.api.controller;

import com.nocountry.api.dto.service.ServiceDTO;
import com.nocountry.api.dto.service.ServiceInfoDTO;
import com.nocountry.api.dto.service.SimpleServiceDTO;
import com.nocountry.api.service.service.ServicesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BookerApp")
public class ServiceController {

    @Autowired
    private ServicesService servicesService;

    /**
     * Allows to get the services of a business by its id by pages.
     *
     * @param businessId ID of the business
     * @param page       Number of page
     * @param size       Size of the page
     * @return List of ServiceDTO
     */
    @GetMapping("/services/business/{business_id}")
    public ResponseEntity<List<SimpleServiceDTO>> getServicesByBusinessIdPageable(
            @PathVariable("business_id") Long businessId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        return new ResponseEntity<List<SimpleServiceDTO>>(servicesService.listByBusinessIdPaginated(businessId, page,
                size), HttpStatus.OK);
    }

    /**
     * Allows to create a service for a business
     *
     * @param serviceInfoDTO ServiceInfoDTO
     * @return ServiceDTO entity
     */
    @PostMapping("/admin/services")
    @Transactional
    public ResponseEntity<SimpleServiceDTO> createService(@RequestBody ServiceInfoDTO serviceInfoDTO) {
        return new ResponseEntity<SimpleServiceDTO>(servicesService.create(serviceInfoDTO), HttpStatus.CREATED);
    }

    /**
     * Allows to get a service by its id
     *
     * @param serviceId ID of service
     * @return ServiceDTO entity
     */
    @GetMapping("/services/{service_id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable("service_id") Long serviceId) {
        return new ResponseEntity<ServiceDTO>(servicesService.getById(serviceId), HttpStatus.OK);
    }

}
