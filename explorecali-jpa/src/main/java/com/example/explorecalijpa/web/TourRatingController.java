package com.example.explorecalijpa.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.explorecalijpa.business.TourRatingService;
import com.example.explorecalijpa.model.TourRating;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Tour Rating Controller
 *
 * Created by Mary Ellen Bowman
 */
@RestController
@Slf4j
@Tag(name = "Tour Rating", description = "The Rating for a Tour API")
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
  private TourRatingService tourRatingService;

  public TourRatingController(TourRatingService tourRatingService) {
    this.tourRatingService = tourRatingService;
  }

  /**
   * Create a Tour Rating.
   *
   * @param tourId
   * @param ratingDto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a Tour Rating")
  public RatingDto createTourRating(@PathVariable(value = "tourId") int tourId,
      @RequestBody @Valid RatingDto ratingDto) {
    log.info("POST /tours/{}/ratings ", tourId);
    TourRating rating = tourRatingService.createNew(tourId, ratingDto.getCustomerId(), 
        ratingDto.getScore(), ratingDto.getComment());
    return new RatingDto(rating);
  }

  @GetMapping
  @Operation(summary = "Lookup All Ratings for a Tour")
  public List<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId) {
    log.info("GET /tours/{}/ratings", tourId);
    List<TourRating> tourRatings = tourRatingService.lookupRatings(tourId);
    return tourRatings.stream().map(RatingDto::new).toList();
  }

  /**
   * Calculate the average Score of a Tour.
   *
   * @param tourId
   * @return the average value.
   */
  @GetMapping("/average")
  @Operation(summary = "Get the Average Score for a Tour")
  public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
    log.info("GET /tours/{}/ratings/average", tourId);
    return Map.of("average", tourRatingService.getAverageScore(tourId));
  }

  /**
   * Update score and comment of a Tour Rating
   *
   * @param tourId
   * @param ratingDto
   * @return The modified Rating DTO.
   */
  @PutMapping
  @Operation(summary = "Modify All Tour Rating Attributes")
  public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid RatingDto ratingDto) {
    log.info("PUT /tours/{}/ratings", tourId);
    return new RatingDto(tourRatingService.update(tourId, ratingDto.getCustomerId(),
                ratingDto.getScore(), ratingDto.getComment()));
  }

  /**
   * Update score or comment of a Tour Rating
   *
   * @param tourId
   * @param ratingDto
   * @return The modified Rating DTO.
   */
  @PatchMapping
  @Operation(summary = "Modify Some Tour Rating Attributes")
  public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId,
      @RequestBody @Valid RatingDto ratingDto) {
    log.info("PATCH /tours/{}/ratings", tourId);
    return new RatingDto(tourRatingService.updateSome(tourId,
        ratingDto.getCustomerId(),
        Optional.ofNullable(ratingDto.getScore()),
        Optional.ofNullable(ratingDto.getComment())));
  }

  /**
   * Delete a Rating of a tour made by a customer
   *
   * @param tourId
   * @param customerId
   */
  @DeleteMapping("/{customerId}")
  @Operation(summary = "Delete a Customer's Rating of a Tour")
  public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
    log.info("DELETE /tours/{}/ratings/{}", tourId, customerId);
    tourRatingService.delete(tourId, customerId);
  }

  /**
   * Create Several Tour Ratings for one tour, score and several customers.
   *
   * @param tourId
   * @param score
   * @param customers
   */
  @PostMapping("/batch")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Give Many Tours Same Score")
  public void createManyTourRatings(@PathVariable(value = "tourId") int tourId,
                                    @RequestParam(value = "score") int score,
                                    @RequestBody List<Integer> customers) {
    log.info("POST /tours/{}/ratings/batch", tourId);
    tourRatingService.rateMany(tourId, score, customers);
  }
}
