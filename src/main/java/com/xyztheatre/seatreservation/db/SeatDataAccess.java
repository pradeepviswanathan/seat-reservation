/*
 * Data Access For Seats
 */
package com.xyztheatre.seatreservation.db;
/**
 * In real world , this class would interact with the actual db
 * This class has been decoupled so that it could interact with any kind of database
 */
import com.xyztheatre.seatreservation.model.Seat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 
 * @author pviswanathan
 */
public class SeatDataAccess implements SeatDataAccessInterface {
    
    private List<Seat> seats;
    
    public SeatDataAccess() {
        this.seats = getAllTheSeatsInTheTheater();   
    }
    
    private  List<Seat> getAllTheSeatsInTheTheater() {
        List<Seat> allSeats = new ArrayList<>();
        for (char row = 'a'; row < 'j'; row++) {
            for (int column =0; column < 80; column++) {
                Seat seat = new Seat(row, column, UUID.randomUUID());
                allSeats.add(seat);
            }
        }
        return allSeats;
    }
    
    
    @Override
    public List<Seat> getAllSeats() {
        return seats;
    }
    
}
