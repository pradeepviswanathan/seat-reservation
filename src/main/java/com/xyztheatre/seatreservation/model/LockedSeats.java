/*
 * Template of locked seats
 */
package com.xyztheatre.seatreservation.model;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author pviswanathan
 */
public class LockedSeats {
    
    /**
     * Says whether the seats have been locked or not
     */
    private Boolean isLockSuccessful;
    
    /**
     * locked seats
     */
    private List<UUID> lockedSeats;



    /**
     * @return the lockedSeats
     */
    public List<UUID> getLockedSeats() {
        return lockedSeats;
    }

    /**
     * @param lockedSeats the lockedSeats to set
     */
    public void setLockedSeats(List<UUID> lockedSeats) {
        this.lockedSeats = lockedSeats;
    }

    /**
     * @return the isLockSuccessful
     */
    public Boolean getIsLockSuccessful() {
        return isLockSuccessful;
    }

    /**
     * @param isLockSuccessful the isLockSuccessful to set
     */
    public void setIsLockSuccessful(Boolean isLockSuccessful) {
        this.isLockSuccessful = isLockSuccessful;
    }
    
}
