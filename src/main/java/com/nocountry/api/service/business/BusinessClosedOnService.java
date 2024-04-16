package com.nocountry.api.service.business;

import com.nocountry.api.dto.business_closed_on.BusinessClosedOnInfoDTO;
import com.nocountry.api.dto.business_closed_on.BusinessClosedOnDTO;

import java.util.List;

public interface BusinessClosedOnService {
    BusinessClosedOnDTO create(BusinessClosedOnInfoDTO businessClosedOnInfoDTO);

    List<BusinessClosedOnDTO> listAllByBusinessId(Long id);

    void deleteById(Long id);
}
