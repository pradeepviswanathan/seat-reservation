/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.seatreservation.service;

import com.xyztheatre.seatreservation.db.SeatDataAccessInterface;
import com.xyztheatre.seatreservation.db.SeatHoldDataAccessInterface;
import com.xyztheatre.seatreservation.model.LockedSeats;
import com.xyztheatre.seatreservation.model.Seat;
import com.xyztheatre.seatreservation.model.SeatHold;
import com.xyztheatre.seatreservation.model.SeatHoldStatus;
import com.xyztheatre.seatreservation.model.TemporaryLockProvider;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author pviswanathan
 */
public class SeatService implements SeatServiceInterface {

    private final SeatDataAccessInterface seatDataAccessInterface;
    private final SeatHoldDataAccessInterface seatHoldDataAccessInterface;
    private final TemporaryLockProvider temporaryLockProvider;
    
    /**
     * constructor
     * @param seatDataAccessInterface
     * @param seatHoldDataAccessInterface
     * @param tempLockProvider 
     */
    public SeatService(SeatDataAccessInterface seatDataAccessInterface,
            SeatHoldDataAccessInterface seatHoldDataAccessInterface,
            TemporaryLockProvider tempLockProvider) {
        this.seatDataAccessInterface = seatDataAccessInterface;
        this.seatHoldDataAccessInterface = seatHoldDataAccessInterface;
        this.temporaryLockProvider = tempLockProvider;
    }

    
    @Override
    public List<Seat> getSeats(int showId, SeatHoldStatus seatHoldStatus) {
        
        // Get all seats in the theatre
        List<Seat> allSeats = seatDataAccessInterface.getAllSeats();

        // Get all seat reservations in the show        
        List<SeatHold> seatHolds  = seatHoldDataAccessInterface.getAllSeatHoldsInTheShow(showId);

        
        if (seatHoldStatus == SeatHoldStatus.AVAILABLE) {
        // finding the seats that are held / reserved
        List<UUID> occupiedSeats = seatHolds.stream().map(a -> a.getSeatIds())
                .flatMap(m -> m.stream())
                .collect(Collectors.toList());
        // Returning all available seats
        return allSeats.stream().filter(a -> !occupiedSeats.contains(a.getId())).collect(Collectors.toList());
        } else {
            
            List<UUID> requestSeatIds = seatHolds.stream()
                    .filter(a -> a.getSeatHoldStatus() == seatHoldStatus)
                    .map(a -> a.getSeatIds())
                    .flatMap(m -> m.stream())
                    .collect(Collectors.toList());
            // Returning all Held / Reserved seats based on the seatHoldStatus
            return allSeats.stream().filter(a -> requestSeatIds.contains(a.getId())).collect(Collectors.toList());
        }
   
    }

    @Override
    public UUID holdSeats(int numberOfSeatsToBeHeld, int showId, String emailAddress) {
       
        List<Seat> allSeats = seatDataAccessInterface.getAllSeats();
        List<SeatHold> seatHolds  = seatHoldDataAccessInterface.getAllSeatHoldsInTheShow(showId);

        // finding the seats that are held / reserved
        List<UUID> occupiedSeats = seatHolds.stream().map(a -> a.getSeatIds())
                .flatMap(m -> m.stream())
                .collect(Collectors.toList());
        // Returning all available seats
        List<Seat> availableSeats =  
                allSeats.stream().filter(a -> !occupiedSeats.contains(a.getId())).collect(Collectors.toList());
  
        // Locking down the seats to prevent double booking of seats by multiple concurrent requests
        LockedSeats lockedSeats = 
                temporaryLockProvider.acquireTemporaryLockOnSeats(showId, numberOfSeatsToBeHeld, availableSeats);
        
        if (!lockedSeats.getIsLockSuccessful()) {
            return null;
        }
   
        SeatHold seatHold = new SeatHold();
        seatHold.setEmailId(emailAddress);
        seatHold.setSeatHoldStatus(SeatHoldStatus.HELD);
        seatHold.setSeatIds(lockedSeats.getLockedSeats());
        seatHold.setShowId(showId);
        seatHold.setSeatHoldId(UUID.randomUUID());       
        
        // Persisting the seat Hold in the database
        seatHoldDataAccessInterface.updateSeatReservationInShow(seatHold);
        
        // Releasing the temporary lock after persisting to the database
        temporaryLockProvider.releaseTemporaryLock(showId, lockedSeats.getLockedSeats());
        return seatHold.getSeatHoldId();
        
    }


    
    @Override
    public Boolean reserveSeats(UUID seatHoldId, int showId) {
   
            SeatHold seatHold = seatHoldDataAccessInterface.getSeatHoldBySeatHoldId(seatHoldId);
            if (seatHold == null) {
                // Document could be expired
                return false;
            }
            if (seatHold.getShowId() != showId) {
                // Request is not valid, showId didn't match
                return false;
            }
            seatHold.setSeatHoldStatus(SeatHoldStatus.RESERVED);
            seatHoldDataAccessInterface.updateSeatReservationInShow(seatHold);
            return true;
    }
    
    
    
   
    
   
}
