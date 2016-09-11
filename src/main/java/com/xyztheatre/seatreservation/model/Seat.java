/*
 * Template of a seat
 */
package com.xyztheatre.seatreservation.model;

import java.util.UUID;

/**
 *
 * @author pviswanathan
 */
public class Seat {
    
    /**
     * row of the seat
     */
    private char row;
    
    /**
     * column of the seat
     */
    private int column;
    
    /**
     * Unique identifier of the seat
     */
    private UUID id;
    
    
    public Seat (char row, int column, UUID id) {
        this.row = row;
        this.column = column;
        this.id = id;
    }

    /**
     * @return the row
     */
    public char getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(char row) {
        this.row = row;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }


}


