package com.example.explorecalijpa.business;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.stereotype.Service;

import com.example.explorecalijpa.model.Tour;
import com.example.explorecalijpa.model.TourRating;
import com.example.explorecalijpa.repo.TourRatingRepository;
import com.example.explorecalijpa.repo.TourRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * Tour Rating Service
 *
 * Created by Mary Ellen Bowman.
 */
@Service
@Slf4j
@Transactional
public class TourRatingService {
  private TourRatingRepository tourRatingRepository;
  private TourRepository tourRepository;

  /**
   * Construct TourRatingService
   *
   * @param tourRatingRepository Tour Rating Repository
   * @param tourRepository       Tour Repository
   */
  public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
    this.tourRatingRepository = tourRatingRepository;
    this.tourRepository = tourRepository;
  }

  /**
   * Create a new Tour Rating in the database
   *
   * @param tourId     tour identifier
   * @param customerId customer identifier
   * @param score      score of the tour rating
   * @param comment    additional comment
   * @throws NoSuchElementException if no Tour found.
   * @return created entity
   */
  public TourRating createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
    log.info("Create a tour rating for tour {} and customer {}", tourId, String.valueOf(customerId));
    return tourRatingRepository.save(new TourRating(verifyTour(tourId), customerId,
        score, comment));
  }

  /**
   * Get a ratings by id.
   *
   * @param id rating identifier
   * @return TourRatings
   */
  public Optional<TourRating> lookupRatingById(int id) {
    log.info("lookup rating by id {}", id);
    return tourRatingRepository.findById(id);
  }

  /**
   * Get All Ratings.
   *
   * @return List of TourRatings
   */
  public List<TourRating> lookupAll() {
    log.info("Lookup all tour ratings");
    return tourRatingRepository.findAll();
  }

  /**
   * Get a page of tour ratings for a tour.
   *
   * @param tourId   tour identifier
   * @param pageable page parameters to determine which elements to fetch
   * @return Page of TourRatings
   * @throws NoSuchElementException if no Tour found.
   */
  public List<TourRating> lookupRatings(int tourId) throws NoSuchElementException {
    log.info("Lookup ratings for tour {}", tourId);
    return tourRatingRepository.findByTourId(verifyTour(tourId).getId());
  }

  /**
   * Update all of the elements of a Tour Rating.
   *
   * @param tourId  tour identifier
   * @param score   score of the tour rating
   * @param comment additional comment
   * @return Tour Rating Domain Object
   * @throws NoSuchElementException if no Tour found.
   */
  public TourRating update(int tourId, Integer customerId, Integer score, String comment)
      throws NoSuchElementException {
    log.info("Update tour {} customer {}", tourId, customerId);
    TourRating rating = verifyTourRating(tourId, customerId);
    rating.setScore(score);
    rating.setComment(comment);
    return tourRatingRepository.save(rating);
  }

  /**
   * Update some of the elements of a Tour Rating.
   *
   * @param tourId     tour identifier
   * @param customerId customer identifier
   * @param score      score of the tour rating
   * @param comment    additional comment
   * @return Tour Rating Domain Object
   * @throws NoSuchElementException if no Tour found.
   */
  public TourRating updateSome(int tourId, Integer customerId, Optional<Integer> score, Optional<String> comment)
      throws NoSuchElementException {
    log.info("Update some of tour {} customer {}", tourId, customerId);
    TourRating rating = verifyTourRating(tourId, customerId);
    score.ifPresent(s ->rating.setScore(s));
    comment.ifPresent(c -> rating.setComment(c));
    return tourRatingRepository.save(rating);
  }

  /**
   * Delete a Tour Rating.
   *
   * @param tourId     tour identifier
   * @param customerId customer identifier
   * @throws NoSuchElementException if no Tour found.
   */
  public void delete(int tourId, Integer customerId) throws NoSuchElementException {
    log.info("Delete rating for tour {} customer {}", tourId, customerId);
    TourRating rating = verifyTourRating(tourId, customerId);
    tourRatingRepository.delete(rating);
  }

  /**
   * Get the average score of a tour.
   *
   * @param tourId tour identifier
   * @return average score as a Double.
   * @throws NoSuchElementException
   */
  public Double getAverageScore(int tourId) throws NoSuchElementException {
    List<TourRating> ratings = tourRatingRepository.findByTourId(verifyTour(tourId).getId());
    OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
    return average.isPresent() ? average.getAsDouble() : null;
  }

  /**
   * Service for many customers to give the same score for a service
   *
   * @param tourId
   * @param score
   * @param customers
   */
  public void rateMany(int tourId,  int score, List<Integer> customers) {
    Tour tour = verifyTour(tourId);
    for (Integer c : customers) {
      if (tourRatingRepository.findByTourIdAndCustomerId(tourId, c).isPresent()) {
        throw new ConstraintViolationException("Unable to create duplicate ratings", null);
      }
      tourRatingRepository.save(new TourRating(tour, c, score));
    }
  }
  /**
   * Verify and return the Tour given a tourId.
   *
   * @param tourId
   * @return the found Tour
   * @throws NoSuchElementException if no Tour found.
   */
  private Tour verifyTour(int tourId) throws NoSuchElementException {
    return tourRepository.findById(tourId)
        .orElseThrow(() -> new NoSuchElementException("Tour does not exist " + tourId));
  }

  /**
   * Verify and return the TourRating for a particular tourId and Customer
   * 
   * @param tourId
   * @param customerId
   * @return the found TourRating
   * @throws NoSuchElementException if no TourRating found
   */
  public TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
    return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
        .orElseThrow(() -> new NoSuchElementException("Tour-Rating pair for request("
            + tourId + " for customer" + customerId));
  }

}
