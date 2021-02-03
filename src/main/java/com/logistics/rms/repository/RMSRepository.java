package com.logistics.rms.repository;

import com.logistics.rms.entity.RateManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RMSRepository extends JpaRepository<RateManagement, Long> {

}
