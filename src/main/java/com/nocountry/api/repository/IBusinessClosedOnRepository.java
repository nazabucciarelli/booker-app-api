package com.nocountry.api.repository;

import com.nocountry.api.model.BusinessClosedOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBusinessClosedOnRepository extends JpaRepository<BusinessClosedOn, Long> {
    List<BusinessClosedOn> findByBusinessId(Long id);
}
