package com.nocountry.api.repository;

import com.nocountry.api.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBusinessRepository extends JpaRepository<Business, Long> {
}

