package com.nocountry.api.repository;

import com.nocountry.api.model.BusinessSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBusinessScheduleRepository extends JpaRepository<BusinessSchedule, Long> {

    List<BusinessSchedule> findByBusinessId(Long id);
}
