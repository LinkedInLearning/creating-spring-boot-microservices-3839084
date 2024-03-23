package com.example.explorecalijpa.web;

import com.example.explorecalijpa.model.TourRating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for Rating a Tour.
 *
 * Created by Mary Ellen Bowman
 */
@Data
public class RatingDto {

  @Min(0)
  @Max(5)
  private Integer score;

  @Size(max = 255)
  private String comment;

  @NotNull
  private Integer customerId;

  /**
   * Constructor to fully initialize the RatingDto
   *
   * @param score      score
   * @param comment    comment
   * @param customerId customer identifier
   */
  public RatingDto(Integer score, String comment, Integer customerId) {
    this.score = score;
    this.comment = comment;
    this.customerId = customerId;
  }

  public RatingDto(TourRating entity) {
    this.score = entity.getScore();
    this.comment = entity.getComment();
    this.customerId = entity.getCustomerId();
  }
}
