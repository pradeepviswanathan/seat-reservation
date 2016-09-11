/*
 * Data Access layer for seat hold data access
 */
package com.xyztheatre.seatreservation.db;
import com.xyztheatre.seatreservation.model.SeatHold;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 *
 * @author pviswanathan
 */
public class SeatHoldDataAccess implements SeatHoldDataAccessInterface {

    // All the reservations are stored in hashmap to mimic the document based data store
    private HashMap<UUID, SeatHold> seatReservations;
    private long seatHoldExpirationTimeInMilliSeconds;
    
    public SeatHoldDataAccess(long seatHoldExpirationTimeInMilliSeconds) {
        // When the application starts ,there will no prebooked reservations
        seatReservations = new HashMap<>();
        this.seatHoldExpirationTimeInMilliSeconds = seatHoldExpirationTimeInMilliSeconds;
    }
    
    
    @Override
    public SeatHold getSeatHoldBySeatHoldId(UUID seatHoldId) {        
        return seatReservations.get(seatHoldId);
    }
    
    @Override
    public List<SeatHold> getAllSeatHoldsInTheShow(int showId) {        
        List<SeatHold> seatHolds = new ArrayList<>();
        if (seatReservations == null) {
                return seatHolds;    
        }
        for(SeatHold seatHold : seatReservations.values()) {
            // Returning only the not expired seta holds for the show
            if (seatHold != null && seatHold.getShowId() == showId && !seatHold.hasTheDocumentExpired()) {
              seatHolds.add(seatHold);  
            }
            
        }
        return seatHolds;
    }

    @Override
    public Boolean updateSeatReservationInShow(SeatHold seatHold) {
        // Setting the current date as document created date
        seatHold.setLastModifiedDate(new Date());
        seatHold.setTimeToLiveInMilliseconds(seatHoldExpirationTimeInMilliSeconds);
        seatReservations.put(seatHold.getSeatHoldId(), seatHold);
        
        return true;
    }
    

    
}
