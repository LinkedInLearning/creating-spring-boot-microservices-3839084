package com.example.explorecalijpa.model;

import jakarta.persistence.*;
import lombok.Data;


/**
 * Rating of a Tour by a Customer
 *
 * Created by Mary Ellen Bowman
 */
@Entity
@Table(name = "tour_rating")
@Data
public class TourRating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "tour_id")
  private Tour tour;

  @Column(name = "customer_id")
  private Integer customerId;

  @Column(nullable = false)
  private Integer score;

  @Column
  private String comment;

  protected TourRating() {
  }

  /**
   * Create a fully initialized TourRating.
   *
   * @param tour       the tour.
   * @param customerId the customer identifier.
   * @param score      Integer score (1-5)
   * @param comment    Optional comment from the customer
   */
  public TourRating(Tour tour, Integer customerId, Integer score, String comment) {
    this.tour = tour;
    this.customerId = customerId;
    this.score = score;
    this.comment = comment;
  }

  /**
   * Create a fully initialized TourRating.
   *
   * @param tour       the tour.
   * @param customerId the customer identifier.
   * @param score      Integer score (1-5)
   */
  public TourRating(Tour tour, Integer customerId, Integer score) {
    this.tour = tour;
    this.customerId = customerId;
    this.score = score;
  }

}
