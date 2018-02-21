package com.process.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.process.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
