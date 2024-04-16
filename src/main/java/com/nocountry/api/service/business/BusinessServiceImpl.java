package com.nocountry.api.service.business;

import com.nocountry.api.dto.business.BusinessDTO;
import com.nocountry.api.exception.ResourceNotFoundException;
import com.nocountry.api.model.*;
import com.nocountry.api.repository.IBusinessRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessServiceImpl implements BusinessService{

    @Autowired
    IBusinessRepository businessRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BusinessDTO> listAll() {
        return businessRepository.findAll()
                .stream()
                .map((business) -> modelMapper.map(business, BusinessDTO.class)).toList();
    }

    @Override
    public BusinessDTO getById(Long id) {
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        if(optionalBusiness.isPresent()) {
            return modelMapper.map(optionalBusiness.get(), BusinessDTO.class);
        }
        throw new ResourceNotFoundException("Business with id " + id + " not found");
    }
}
