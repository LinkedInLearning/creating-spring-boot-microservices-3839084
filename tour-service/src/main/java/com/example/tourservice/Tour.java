package com.example.tourservice;

/**
 * The Tour contains all attributes of an Explore California Tour.
 *
 * Created by Mary Ellen Bowman
 */
public record Tour(String title, Integer price, Boolean kidFriendly) {
  @Override
  public String toString() {
    return String.format("%s\t$%d\tKid Friendly: %s",
        title(), price(), kidFriendly ? "Yes" : "No");
  }
}
