package com.nocountry.api.service.business;

import com.nocountry.api.dto.business_schedule.BusinessScheduleInfoDTO;
import com.nocountry.api.dto.business_schedule.BusinessScheduleDTO;
import com.nocountry.api.exception.NotValidDatetimeException;
import com.nocountry.api.exception.ResourceNotFoundException;
import com.nocountry.api.model.Business;
import com.nocountry.api.model.BusinessSchedule;
import com.nocountry.api.repository.IBusinessRepository;
import com.nocountry.api.repository.IBusinessScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessScheduleServiceImpl implements BusinessScheduleService {

    @Autowired
    IBusinessScheduleRepository businessScheduleRepository;

    @Autowired
    IBusinessRepository businessRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BusinessScheduleDTO create(BusinessScheduleInfoDTO businessScheduleInfoDTO) {
        Optional<Business> optionalBusiness = businessRepository.findById(businessScheduleInfoDTO.getBusinessId());
        if (optionalBusiness.isEmpty()) {
            throw new ResourceNotFoundException("Business with ID " + businessScheduleInfoDTO.getBusinessId() + " not found");
        } else if (businessScheduleInfoDTO.getFromHour().isAfter(businessScheduleInfoDTO.getToHour())) {
            throw new NotValidDatetimeException("The fromHour value must be before of the toHour value");
        }
        BusinessSchedule businessSchedule = new BusinessSchedule(optionalBusiness.get(), businessScheduleInfoDTO.getFromHour(), businessScheduleInfoDTO.getToHour());
        businessScheduleRepository.save(businessSchedule);
        return modelMapper.map(businessSchedule, BusinessScheduleDTO.class);
    }

    @Override
    public List<BusinessScheduleDTO> listAllByBusinessId(Long id) {
        List<BusinessSchedule> businessScheduleList = businessScheduleRepository.findByBusinessId(id);
        if (businessScheduleList.isEmpty()) {
            throw new ResourceNotFoundException("Business with ID " + id + " has no business schedules yet");
        }
        return businessScheduleList.stream()
                .map(businessSchedule -> modelMapper.map(businessSchedule, BusinessScheduleDTO.class))
                .sorted((o1, o2) ->
                        o1.getFromHour().isAfter(o2.getFromHour()) ? 1 : o1.getFromHour().equals(o2.getFromHour()) ? 0 : -1)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        Optional<BusinessSchedule> businessSchedule = businessScheduleRepository.findById(id);
        if (businessSchedule.isEmpty()) {
            throw new ResourceNotFoundException("Business schedule with ID " + id + " not found");
        }
        businessScheduleRepository.delete(businessSchedule.get());
    }
}
