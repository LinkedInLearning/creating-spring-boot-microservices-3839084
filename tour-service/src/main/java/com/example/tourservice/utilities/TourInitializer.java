package com.example.tourservice.utilities;

import com.example.tourservice.Tour;
import com.example.tourservice.TourType;

public class TourInitializer {
    private TourRepository tourRepository;
    public TourInitializer(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
        tourRepository.save(new Tour("Big Sur Retreat", 750, TourType.BACKPACKING));
        tourRepository.save(new Tour("In the Steps of John Muir", 500, TourType.BACKPACKING));
        tourRepository.save(new Tour("The Death Valley Survivor's Trek", 200, TourType.BACKPACKING));
        tourRepository.save(new Tour( "Day Spa Package", 200, TourType.CALM));
        tourRepository.save(new Tour("Restoration Package", 350, TourType.CALM));
        tourRepository.save(new Tour("Monterey to Santa Barbara Tour", 550, TourType.CYCLING));
        tourRepository.save(new Tour( "Cycle California: My Way", 750, TourType.CYCLING));
        tourRepository.save(new Tour("Kids L.A. Tour", 100, TourType.KIDS));
        tourRepository.save(new Tour("Islands of the Blue Dolphins Tour", 200, TourType.KIDS));
        tourRepository.save(new Tour("Eco-Tour", 400, TourType.KIDS));
        tourRepository.save(new Tour("Endangered Species Expedition", 250, TourType.NATURE));
    } 
}
