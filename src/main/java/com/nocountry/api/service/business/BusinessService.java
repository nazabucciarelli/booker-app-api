package com.nocountry.api.service.business;

import com.nocountry.api.dto.business.BusinessDTO;

import java.util.List;

public interface BusinessService {
    BusinessDTO getById(Long id);

    List<BusinessDTO> listAll();
}
