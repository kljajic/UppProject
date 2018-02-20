package com.process.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.process.model.PurchaseRequest;

@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {

}
