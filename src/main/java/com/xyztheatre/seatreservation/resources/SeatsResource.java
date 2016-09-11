/*
 * Contains endpoints to Get seats in the show , hold seats in the show and reserve seats in the show
 * Loging with log4j can be added for this application 
 * and I would requestId to each log to identify logs created from a specific request
 */
package com.xyztheatre.seatreservation.resources;

import com.xyz.seatreservation.service.SeatServiceInterface;
import com.xyztheatre.seatreservation.model.Seat;
import com.xyztheatre.seatreservation.model.SeatHold;
import com.xyztheatre.seatreservation.model.SeatHoldStatus;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pviswanathan
 */
@Path("/shows")
@Produces(MediaType.APPLICATION_JSON)
public class SeatsResource {
    
    private SeatServiceInterface seatReservationInterface;
    
    /**
     * constructor
     * @param seatReservationInterface  service interface
     */
    public SeatsResource(SeatServiceInterface seatReservationInterface) {
        this.seatReservationInterface = seatReservationInterface;
    }
    
    /**
     * returns seats in the show with the requested status
     * @param showId unique identifier of the show
     * @param seatHoldStatus seat hold status - can be available/ held / reserved
     * @return 
     */
    @GET
    @Path("/{showId}/Seats")
    public Response getAvailableSeatsInShows(@PathParam("showId") int showId, 
            @QueryParam("status") SeatHoldStatus seatHoldStatus) {
        // Request with parameters can be logged here
        try {
            List<Seat> seats = seatReservationInterface.getSeats(showId, seatHoldStatus);
            return Response.ok(seats).build();
        } catch (Exception ex) {
            // exception message and stack trace can be logged here
            return Response.serverError().build();
        }
    }
    
    /**
     * hold seats in the show
     * @param showId unique identifier of the show
     * @param seatHold contains the email address and number of seats to be held information
     * @return 
     */
    @POST
    @Path("/{showId}/holdSeats")
    public Response holdSeatsInShow(@PathParam("showId") @NotNull int showId, 
            @Valid @NotNull SeatHold seatHold) {
        // Request with parameters can be logged here
        try {
            UUID seatHoldId = seatReservationInterface.holdSeats(seatHold.getNumberOfSeatsToHold(), showId, seatHold.getEmailId());
            if (seatHoldId == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            // Response can be logged here
            return Response.created(null).header("seatHoldId", seatHoldId).build();
        } catch (Exception ex) {
            // exception message and stack trace can be logged here
            return Response.serverError().build();
        }
        
    }
    
    /**
     * reserves the seats
     * @param showId unique identifier of the show
     * @param seatHoldId seat hold Id from the hold seats request
     * @return 
     */
    @PUT
    @Path("/{showId}/reserveSeats/{seatHoldId}")
    public Response reserveSeatsInShow(@PathParam("showId") @NotNull int showId, 
            @PathParam("seatHoldId") @NotNull UUID seatHoldId) {
        // Request with parameters can be logged here
        try {
            Boolean isReserveSeatsSuccessful = seatReservationInterface.reserveSeats(seatHoldId, showId);
            if (!isReserveSeatsSuccessful) {
                // isReserveSeatsSuccessful will be false only when show or seatHoldId doesn't exist
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            // exception message and stack trace can be logged here
            return Response.serverError().build();
        }
        return Response.noContent().build();
    }
    
}
