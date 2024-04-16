package com.nocountry.api.service.business;

import com.nocountry.api.dto.business_closed_on.BusinessClosedOnInfoDTO;
import com.nocountry.api.dto.business_closed_on.BusinessClosedOnDTO;
import com.nocountry.api.exception.NotValidDatetimeException;
import com.nocountry.api.exception.ResourceNotFoundException;
import com.nocountry.api.model.Business;
import com.nocountry.api.model.BusinessClosedOn;
import com.nocountry.api.repository.IBusinessClosedOnRepository;
import com.nocountry.api.repository.IBusinessRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessClosedOnServiceImpl implements BusinessClosedOnService {

    @Autowired
    IBusinessClosedOnRepository businessClosedOnRepository;

    @Autowired
    IBusinessRepository businessRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BusinessClosedOnDTO create(BusinessClosedOnInfoDTO businessClosedOnInfoDTO) {
        boolean dateHasPassed = businessClosedOnInfoDTO.getDate().isBefore(LocalDate.now());
        if (dateHasPassed) {
            throw new NotValidDatetimeException("Date " + businessClosedOnInfoDTO.getDate().toString() + " has already passed");
        }
        Optional<Business> business = businessRepository.findById(businessClosedOnInfoDTO.getBusinessId());
        if (business.isEmpty()) {
            throw new ResourceNotFoundException("Business with ID " + businessClosedOnInfoDTO.getBusinessId() + " not found");
        }
        BusinessClosedOn businessClosedOn = new BusinessClosedOn(business.get(), businessClosedOnInfoDTO.getDate(),
                businessClosedOnInfoDTO.getFromHour(), businessClosedOnInfoDTO.getToHour());
        businessClosedOnRepository.save(businessClosedOn);
        return modelMapper.map(businessClosedOn, BusinessClosedOnDTO.class);
    }

    @Override
    public List<BusinessClosedOnDTO> listAllByBusinessId(Long id) {
        List<BusinessClosedOn> businessClosedOnList = businessClosedOnRepository.findByBusinessId(id);
        if (businessClosedOnList.isEmpty()) {
            throw new ResourceNotFoundException("There are no dates of closed business with business ID " + id);
        }
        return businessClosedOnList
                .stream()
                .map((businessClosedOn -> modelMapper.map(businessClosedOn, BusinessClosedOnDTO.class))).toList();
    }

    @Override
    public void deleteById(Long id) {
        Optional<BusinessClosedOn> businessClosedOnDTO = businessClosedOnRepository.findById(id);
        if (businessClosedOnDTO.isEmpty()) {
            throw new ResourceNotFoundException("There are no dates of closed business with ID " + id);
        }
        businessClosedOnRepository.delete(businessClosedOnDTO.get());
    }

}
