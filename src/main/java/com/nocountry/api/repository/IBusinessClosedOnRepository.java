package com.nocountry.api.repository;

import com.nocountry.api.model.BusinessClosedOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBusinessClosedOnRepository extends JpaRepository<BusinessClosedOn, Long> {
    List<BusinessClosedOn> findByBusinessId(Long id);

    boolean existsByDateAndBusinessId(LocalDate date, Long businessId);

    Optional<BusinessClosedOn> findByDateAndBusinessId(LocalDate date, Long businessId);
}
