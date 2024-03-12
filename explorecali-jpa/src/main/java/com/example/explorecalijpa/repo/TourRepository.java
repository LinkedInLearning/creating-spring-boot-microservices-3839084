package com.example.explorecalijpa.repo;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.explorecalijpa.model.Tour;

public interface TourRepository extends PagingAndSortingRepository<Tour, Integer>{
  
}
