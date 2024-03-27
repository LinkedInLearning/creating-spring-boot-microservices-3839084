package com.example.explorecaliimages.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.explorecaliimages.model.IdName;
import com.example.explorecaliimages.model.Image;

public interface ImageRepository  extends MongoRepository<Image, String> {
  Optional<Image> findByFileName(String name);
  
  List<IdName> findIdNameBy();
}
