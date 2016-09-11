/*
 * Interface for seat hold data access
 */
package com.xyztheatre.seatreservation.db;

import com.xyztheatre.seatreservation.model.SeatHold;
import java.util.List;
import java.util.UUID;


/**
 *
 * @author pviswanathan
 */
public interface SeatHoldDataAccessInterface {
    
    
    /**
     * Gets the seatHold document by seat Hold Id
     * @param seatHoldId seatHoldId
     * @return 
     */
    SeatHold getSeatHoldBySeatHoldId(UUID seatHoldId);
    
    /**
     * gets all the reservations for the specific show
     * @param showId unique identifier of the show
     * @return 
     */
    public List<SeatHold> getAllSeatHoldsInTheShow(int showId);
    
    /**
     * updates the reservation in data store
     * @param seatHold
     * @return 
     */
    Boolean updateSeatReservationInShow(SeatHold seatHold);
}
