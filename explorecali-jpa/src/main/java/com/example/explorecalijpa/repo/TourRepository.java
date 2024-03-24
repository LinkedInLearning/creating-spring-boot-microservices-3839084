package com.example.explorecalijpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.explorecalijpa.model.Difficulty;
import com.example.explorecalijpa.model.Tour;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tours", description = "The Tour API")
public interface TourRepository extends JpaRepository<Tour, Integer> {
  List<Tour> findByDifficulty(Difficulty diff);
  List<Tour> findByTourPackageCode(String code);
}