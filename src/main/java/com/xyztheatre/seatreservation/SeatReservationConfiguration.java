package com.xyztheatre.seatreservation;

import io.dropwizard.Configuration;
import javax.validation.constraints.NotNull;


public class SeatReservationConfiguration extends Configuration {
    
    @NotNull
    private long seatHoldExpirationTimeInMilliSeconds;

    /**
     * @return the seatHoldExpirationTimeInMilliSeconds
     */
    public long getSeatHoldExpirationTimeInMilliSeconds() {
        return seatHoldExpirationTimeInMilliSeconds;
    }

    /**
     * @param seatHoldExpirationTimeInMilliSeconds the seatHoldExpirationTimeInMilliSeconds to set
     */
    public void setSeatHoldExpirationTimeInMilliSeconds(long seatHoldExpirationTimeInMilliSeconds) {
        this.seatHoldExpirationTimeInMilliSeconds = seatHoldExpirationTimeInMilliSeconds;
    }
}
