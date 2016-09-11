package com.xyztheatre.seatreservation;

import com.xyz.seatreservation.service.SeatService;
import com.xyz.seatreservation.service.SeatServiceInterface;
import com.xyztheatre.seatreservation.db.SeatDataAccess;
import com.xyztheatre.seatreservation.db.SeatDataAccessInterface;
import com.xyztheatre.seatreservation.db.SeatHoldDataAccess;
import com.xyztheatre.seatreservation.db.SeatHoldDataAccessInterface;
import com.xyztheatre.seatreservation.model.Seat;
import com.xyztheatre.seatreservation.model.SeatHold;
import com.xyztheatre.seatreservation.model.TemporaryLockProvider;
import com.xyztheatre.seatreservation.resources.SeatsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SeatReservationApplication extends Application<SeatReservationConfiguration> {

    public static void main(final String[] args) throws Exception {
        new SeatReservationApplication().run(args);
    }

    @Override
    public String getName() {
        return "SeatReservation";
    }

    @Override
    public void initialize(final Bootstrap<SeatReservationConfiguration> bootstrap) {
        // TODO: application initialization
     
    }

    @Override
    public void run(final SeatReservationConfiguration configuration,
                    final Environment environment) {

        // Creating Seat DataAccess
        SeatDataAccessInterface seatDataAccessInterface = new SeatDataAccess();
        // Creating Seat hold Data Access
        SeatHoldDataAccessInterface seatHoldDataAccessInterface = 
                new SeatHoldDataAccess(configuration.getSeatHoldExpirationTimeInMilliSeconds());
        
        // Creating lock provider
        TemporaryLockProvider temporaryLockProvider = new TemporaryLockProvider();
        
        // Creating Service Interface
        SeatServiceInterface seatServiceInterface = 
                new SeatService(seatDataAccessInterface, seatHoldDataAccessInterface, temporaryLockProvider);
        
        // Creating the service
        final SeatsResource seatsResource = new SeatsResource(seatServiceInterface);
               
        // Registering the service
        environment.jersey().register(seatsResource);
        
    }
    
    


}
