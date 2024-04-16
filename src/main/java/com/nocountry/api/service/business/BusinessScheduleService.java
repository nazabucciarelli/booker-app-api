package com.nocountry.api.service.business;

import com.nocountry.api.dto.business_schedule.BusinessScheduleInfoDTO;
import com.nocountry.api.dto.business_schedule.BusinessScheduleDTO;

import java.util.List;

public interface BusinessScheduleService {

    BusinessScheduleDTO create(BusinessScheduleInfoDTO businessScheduleInfoDTO);

    List<BusinessScheduleDTO> listAllByBusinessId(Long id);

    void deleteById(Long id);
}
