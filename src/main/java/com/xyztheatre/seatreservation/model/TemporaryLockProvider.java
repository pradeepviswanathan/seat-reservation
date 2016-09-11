/*
 * Class contains methods to acquire a release a lock.
 * In real world , lock would be stored in a data store
 * Here  it is being stored in the memory
 * The purpose of this class is to ensure that seats are not double booked with concurrent requests
 */
package com.xyztheatre.seatreservation.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author pviswanathan
 */
public class TemporaryLockProvider {

    /**
     * Map between Show Id and "Seats To Hold"
     */
    public static HashMap<Integer, HashSet<UUID>> ShowSeatsToHoldMap = new HashMap<>();

    public LockedSeats acquireTemporaryLockOnSeats(int showId, int numberOfSeatsToHold, List<Seat> availableSeats) {

        HashSet<UUID> seatsThatAreLocked = getLockedSeatsFortheShow(showId);
        int numberOfSeatsLocked = seatsThatAreLocked.size();
        
        // Checking whether seats are available to be locked
        // Availability condition : Available Seats - locked down Seats should be greater than number os seats requested
        if ((availableSeats.size() - numberOfSeatsLocked) < numberOfSeatsToHold) {
            LockedSeats lockedSeats = new LockedSeats();
            lockedSeats.setIsLockSuccessful(Boolean.FALSE);
            return lockedSeats;
        }

        // Selecting the seats from the top level
        // For enhancement , we could add a order to the Seat Object and allow the seats to be booked in that order
        List<UUID> seatsToHold
                = availableSeats.stream().filter(b -> !seatsThatAreLocked.contains(b.getId()))
                .map(a -> a.getId())
                .limit(numberOfSeatsToHold)
                .collect(Collectors.toList());
        
        HashSet<UUID> newLockedSeatSet = new HashSet<>();       
        newLockedSeatSet.addAll(seatsToHold);
        newLockedSeatSet.addAll(seatsThatAreLocked);
        // Adding the "To be Held" Seats in the HashMap with Key as the show id
        ShowSeatsToHoldMap.put(showId, newLockedSeatSet);

        LockedSeats lockedSeats = new LockedSeats();
        lockedSeats.setIsLockSuccessful(Boolean.TRUE);
        lockedSeats.setLockedSeats(seatsToHold);
        return lockedSeats;

    }
    
    /**
     * gets seats that are currently locked - (These seats are in the process of being held)
     * @param showId unique identifier of the show
     * @return 
     */
    private HashSet<UUID> getLockedSeatsFortheShow(int showId) {
        HashSet<UUID> seatsThatAreLocked = ShowSeatsToHoldMap.get(showId);
        seatsThatAreLocked = seatsThatAreLocked == null ? new HashSet<>() : seatsThatAreLocked;
        return seatsThatAreLocked;
    }
    
    /**
     * releasing the temporary lock.
     * @param showId unique identifier of the show
     * @param seatIds seats to be released
     */
    public void releaseTemporaryLock(int showId , List<UUID> seatIds) {
        HashSet<UUID> seatsThatAreLocked = getLockedSeatsFortheShow(showId); 
        seatsThatAreLocked.removeAll(seatIds);
        ShowSeatsToHoldMap.put(showId, seatsThatAreLocked);
        
    }

}
