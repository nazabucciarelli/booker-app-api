package com.nocountry.api.service.service;

import com.nocountry.api.dto.service.ServiceDTO;
import com.nocountry.api.dto.service.ServiceInfoDTO;
import com.nocountry.api.dto.service.SimpleServiceDTO;
import com.nocountry.api.exception.ResourceNotFoundException;
import com.nocountry.api.model.Business;
import com.nocountry.api.model.Service;
import com.nocountry.api.repository.IBusinessRepository;
import com.nocountry.api.repository.IServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServicesServiceImpl implements ServicesService {
    @Autowired
    private IServiceRepository serviceRepository;
    @Autowired
    private IBusinessRepository businessRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SimpleServiceDTO> listByBusinessIdPaginated(Long id, int page, int size) {
        List<Service> services = serviceRepository.findAllByBusinessId(id);

        if (services.isEmpty())
            return null;

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Service> servicesPage = serviceRepository.findAllByBusinessId(id, pageable);

            if (!servicesPage.isEmpty()) {
                return servicesPage.stream().
                        map(service -> modelMapper.map(service, SimpleServiceDTO.class)).
                        collect(Collectors.toList());
            }
        } catch (ResourceNotFoundException rnf) {
            throw new ResourceNotFoundException(rnf.getMessage());
        }

        return null;
    }

    @Override
    public SimpleServiceDTO create(ServiceInfoDTO serviceInfoDTO) {
        Optional<Business> optionalBusiness = businessRepository.findById(serviceInfoDTO.getBusinessId());
        if (optionalBusiness.isEmpty()) {
            throw new ResourceNotFoundException("Business with ID " + serviceInfoDTO.getBusinessId() + " does not exist");
        }
        Service service = new Service(optionalBusiness.get(), serviceInfoDTO.getName(), serviceInfoDTO.getDescription(),
                serviceInfoDTO.getPrice(), serviceInfoDTO.getDurationMinutes());
        return modelMapper.map(serviceRepository.save(service),SimpleServiceDTO.class);
    }

    @Override
    public ServiceDTO getById(Long id) {
        Optional<Service> optionalService = serviceRepository.findById(id);
        if (optionalService.isEmpty()) {
            throw new ResourceNotFoundException("Service with ID " + id + " does not exist");
        }
        return modelMapper.map(optionalService.get(), ServiceDTO.class);
    }
}
