/*
 * Interface for seat data
 */
package com.xyztheatre.seatreservation.db;

import com.xyztheatre.seatreservation.model.Seat;
import java.util.List;

/**
 *
 * @author pviswanathan
 */
public interface SeatDataAccessInterface {
    /**
     * Gets all seats for the show
     * There is no showId here, for simplicity I have assumed all the shows
     * have the same set of seats
     * @return 
     */
    List<Seat> getAllSeats();
}
