package com.nocountry.api.repository;


import com.nocountry.api.model.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServiceRepository extends JpaRepository<Service, Long> {

    Page<Service> findAllByBusinessId(Long id, Pageable pageable);

    Long countByBusinessId(Long id);

    List<Service> findAllByBusinessId(Long id);
}
