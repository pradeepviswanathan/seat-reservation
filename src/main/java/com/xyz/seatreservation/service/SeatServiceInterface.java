/*
 * This is the interface in the problem
 * Have added some more functionality based on the assumptions
 */
package com.xyz.seatreservation.service;

import com.xyztheatre.seatreservation.model.Seat;
import com.xyztheatre.seatreservation.model.SeatHoldStatus;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author pviswanathan
 */
public interface SeatServiceInterface {
    /**
     * Get all the Seats for the show based on the SeatHoldStatus in the request
     * @param showId Unique Identifier of the show
     * @param seatHoldStatus Seat hold Status - AVAILABLE / HELD / RESERVED
     * @return 
     */
    List<Seat> getSeats(int showId, SeatHoldStatus seatHoldStatus);
    
    /**
     * holds the requested number of seats in the show
     * @param numberOfSeatsToBeHeld number of seats to be held
     * @param showId Unique Identifier of the show
     * @param emailAddress email Address of the reservation
     * @return Unique Identifier for the seat hold transaction
     */
    UUID holdSeats(int numberOfSeatsToBeHeld, int showId, String emailAddress);
    
    
    /**
     * reserves the seats in a specific seat hold transaction
     * @param seatHoldId Unique Identifier for the seat hold transaction
     * @param showId Unique Identifier of the show 
     * @return true/false based on the reservation was successful or not
     */
    Boolean reserveSeats(UUID seatHoldId, int showId);
}
