package com.example.explorecalijpa.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.explorecalijpa.model.TourPackage;
import com.example.explorecalijpa.repo.TourPackageRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class TourPackageService {
  private TourPackageRepository tourPackageRepository;

  public TourPackageService(TourPackageRepository tourPackageRepository) {
    this.tourPackageRepository = tourPackageRepository;
  }

  public TourPackage createTourPackage(String code, String name) {
    log.info("Create tour package {}:{}",code, name);
    return tourPackageRepository.findById(code)
        .orElse(tourPackageRepository.save(new TourPackage(code, name)));
  }

  public List<TourPackage> lookupAll() {
    log.info("Lookup all");
    return tourPackageRepository.findAll();
  }

  public long total() {
    log.info("Get total tour package");
    return tourPackageRepository.count();
  }
}
