package com.example.explorecalijpa.repo;

import com.example.explorecalijpa.model.TourPackage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Challenge: Change url keyword to "packages"
 */
public interface TourPackageRepository extends JpaRepository<TourPackage, String> {
  Optional<TourPackage> findByName(String name);
}
