package com.anil.swiftBus.cron;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.TripDAO;
import com.anil.swiftBus.entity.Trip;
import com.anil.swiftBus.enums.TripStatus;

@Component
public class TripStatusScheduler {

    private final TripDAO tripDAO;

    public TripStatusScheduler(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    @Scheduled(fixedRate = 300000) // every 5 minutes
    @Transactional
    public void autoCompleteTrips() {
        LocalDateTime now = LocalDateTime.now();
        List<Trip> trips = tripDAO.findByStatusAndArrivalDatetimeBefore(TripStatus.SCHEDULED, now);
        for (Trip trip : trips) {
            trip.setStatus(TripStatus.COMPLETED);
            tripDAO.save(trip);
        }
    }
}
