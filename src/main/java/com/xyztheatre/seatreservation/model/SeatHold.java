/*
 * Template of SeatHold
 */
package com.xyztheatre.seatreservation.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author pviswanathan
 */
public class SeatHold {
    
    // This property is used only in the Post
    // says the number of seats to be held
    private int numberOfSeatsToHold;
    
// Unique seat reservation Id
    private UUID seatHoldId;
    // Email Id in the reservation
    private String emailId;
    // Seat Ids that are reserved
    private List<UUID> seatIds;
    // Status of seat hold - can be held / reserved
    private SeatHoldStatus seatHoldStatus;

    private int showId;
    
    private Date lastModifiedDate;
    
    private long TimeToLiveInMilliseconds;
    
    public Boolean hasTheDocumentExpired() {
        if (this.seatHoldStatus != SeatHoldStatus.HELD) {
            // Only held documents are supposed to expire
            // In real world , if we use a document data base, we could set the ttl on the document
            return false;
        }
        Date currentDate = new Date();
        // Calculating expiry based on ttl
        Date expiryDate = new Date(lastModifiedDate.getTime() + TimeToLiveInMilliseconds);
        // If current date is after the expiry date, then the document has expired
        return currentDate.after(expiryDate);
        
    }

    /**
     * @return the seatIds
     */
    public List<UUID> getSeatIds() {
        return seatIds;
    }

    /**
     * @param seatIds the seatIds to set
     */
    public void setSeatIds(List<UUID> seatIds) {
        this.seatIds = seatIds;
    }

    /**
     * @return the seatHoldStatus
     */
    public SeatHoldStatus getSeatHoldStatus() {
        return seatHoldStatus;
    }

    /**
     * @param seatHoldStatus the seatHoldStatus to set
     */
    public void setSeatHoldStatus(SeatHoldStatus seatHoldStatus) {
        this.seatHoldStatus = seatHoldStatus;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the seatHoldId
     */
    public UUID getSeatHoldId() {
        return seatHoldId;
    }

    /**
     * @param seatHoldId the seatHoldId to set
     */
    public void setSeatHoldId(UUID seatHoldId) {
        this.seatHoldId = seatHoldId;  
    }

    /**
     * @return the numberOfSeatsToHold
     */
    public int getNumberOfSeatsToHold() {
        return numberOfSeatsToHold;
    }

    /**
     * @param numberOfSeatsToHold the numberOfSeatsToHold to set
     */
    public void setNumberOfSeatsToHold(int numberOfSeatsToHold) {
        this.numberOfSeatsToHold = numberOfSeatsToHold;
  }

    /**
     * @return the showId
     */
    public int getShowId() {
        return showId;
    }

    /**
     * @param showId the showId to set
     */
    public void setShowId(int showId) {
        this.showId = showId;
    }



    /**
     * @param TimeToLiveInMilliseconds the TimeToLiveInMilliseconds to set
     */
    public void setTimeToLiveInMilliseconds(long TimeToLiveInMilliseconds) {
        this.TimeToLiveInMilliseconds = TimeToLiveInMilliseconds;
    }

    /**
     * @return the lastModifiedDate
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * @param lastModifiedDate the lastModifiedDate to set
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    
    
}
