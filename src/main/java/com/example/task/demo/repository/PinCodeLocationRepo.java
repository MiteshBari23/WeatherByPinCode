package com.example.task.demo.repository;

import com.example.task.demo.entity.PincodeLocationData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PinCodeLocationRepo extends JpaRepository<PincodeLocationData, Long>{
    Optional<PincodeLocationData> findByPincode(String pincode);
}
