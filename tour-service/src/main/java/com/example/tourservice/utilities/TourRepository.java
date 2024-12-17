package com.example.tourservice.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.tourservice.Tour;


@Repository
public class TourRepository {
    private final List<Tour> tours = new ArrayList<>();

    public TourRepository() {
    }

    public Tour save(Tour tour) {
        if (!tours.contains(tour)) {
            tours.add(tour);
        }
        return tour;
    }

    public List<Tour> findAll() {
        return tours;
    }

    public List<Tour> findByType(Boolean isKidFriendly) {
        return tours.stream().filter(t -> t.kidFriendly() == isKidFriendly).toList();
    }
}
