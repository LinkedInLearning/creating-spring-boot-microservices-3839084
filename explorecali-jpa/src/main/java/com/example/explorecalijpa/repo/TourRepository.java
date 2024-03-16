package com.example.explorecalijpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.explorecalijpa.model.Tour;

public interface TourRepository extends JpaRepository<Tour, Integer> {

}