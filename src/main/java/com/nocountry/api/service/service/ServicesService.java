package com.nocountry.api.service.service;

import com.nocountry.api.dto.service.ServiceDTO;
import com.nocountry.api.dto.service.ServiceInfoDTO;
import com.nocountry.api.dto.service.SimpleServiceDTO;
import com.nocountry.api.model.Service;

import java.util.List;

public interface ServicesService {

    List<SimpleServiceDTO> listByBusinessIdPaginated(Long id, int page, int items);

    SimpleServiceDTO create(ServiceInfoDTO serviceInfoDTO);

    ServiceDTO getById(Long id);
}
