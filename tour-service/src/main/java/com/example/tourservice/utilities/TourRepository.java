package com.example.tourservice.utilities;

import java.util.*;

import com.example.tourservice.Tour;
import com.example.tourservice.TourType;

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

    public List<Tour> findByCategory(TourType tc) {
        return tours.stream().filter(t -> t.type().equals(tc)).toList();
    }
}
