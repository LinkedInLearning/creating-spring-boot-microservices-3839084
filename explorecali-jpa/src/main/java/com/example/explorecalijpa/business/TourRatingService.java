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

/**
 * Tour Rating Service
 *
 * Created by Mary Ellen Bowman.
 */
@Service
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
    return tourRatingRepository.findById(id);
  }

  /**
   * Get All Ratings.
   *
   * @return List of TourRatings
   */
  public List<TourRating> lookupAll() {
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
