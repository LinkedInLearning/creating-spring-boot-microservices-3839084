package com.example;
import com.example.tourservice.TravelAgentService;
import com.example.tourservice.utilities.TourInitializer;
import com.example.tourservice.utilities.TourRepository;

public class Application {
    public static void main(String[] args) {
        TourRepository tr = new TourRepository();
        TourInitializer as = new TourInitializer(tr);
        TravelAgentService agentService = new TravelAgentService(tr);
        agentService.displayTours();
    }
}