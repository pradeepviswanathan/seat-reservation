package com.xyztheatre.seatreservation.service;

import com.xyz.seatreservation.service.SeatService;
import com.xyz.seatreservation.service.SeatServiceInterface;
import com.xyztheatre.seatreservation.db.SeatDataAccessInterface;
import com.xyztheatre.seatreservation.db.SeatHoldDataAccessInterface;
import com.xyztheatre.seatreservation.model.LockedSeats;
import com.xyztheatre.seatreservation.model.Seat;
import com.xyztheatre.seatreservation.model.SeatHold;
import com.xyztheatre.seatreservation.model.SeatHoldStatus;
import com.xyztheatre.seatreservation.model.TemporaryLockProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author pviswanathan
 */
public class SeatReservarionServiceTest {

    public SeatReservarionServiceTest() {

    }

    @Mock
    private SeatDataAccessInterface seatDataAccessInteface;

    @Mock
    private SeatHoldDataAccessInterface seatHoldDataAccessInterface;

    private SeatServiceInterface seatService;

    private final UUID SEAT1_ID = UUID.fromString("02b9573e-77ae-11e6-8b77-86f30ca893d3");
    private final UUID SEAT2_ID = UUID.fromString("02b959dc-77ae-11e6-8b77-86f30ca893d3");
    private final UUID SEAT3_ID = UUID.fromString("02b959dc-77ae-11e6-8b77-86f30ca22222");
    private final int SHOW_ID = 1;
    Seat seat1 = new Seat('a', 1, SEAT1_ID);
    Seat seat2 = new Seat('b', 2, SEAT2_ID);
    Seat seat3 = new Seat('c', 2, SEAT3_ID);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        TemporaryLockProvider tempLockProvider = new TemporaryLockProvider();
        seatService = new SeatService(seatDataAccessInteface, seatHoldDataAccessInterface, tempLockProvider);
        List<Seat> seats = new ArrayList<>();
        seats.add(seat1);
        seats.add(seat2);
        seats.add(seat3);
        when(seatDataAccessInteface.getAllSeats()).thenReturn(seats);

        List<SeatHold> seatHolds = new ArrayList<>();

        SeatHold seatHold1 = new SeatHold();
        seatHold1.setSeatHoldId(UUID.randomUUID());
        List<UUID> heldSeatIds = new ArrayList<>();
        heldSeatIds.add(SEAT1_ID);
        seatHold1.setSeatIds(heldSeatIds);
        seatHold1.setSeatHoldStatus(SeatHoldStatus.HELD);
        seatHolds.add(seatHold1);

        SeatHold seatHold2 = new SeatHold();
        seatHold2.setSeatHoldId(UUID.randomUUID());
        List<UUID> reservedSeatIds = new ArrayList<>();
        reservedSeatIds.add(SEAT2_ID);
        seatHold2.setSeatIds(reservedSeatIds);
        seatHold2.setSeatHoldStatus(SeatHoldStatus.RESERVED);

        seatHolds.add(seatHold2);


        when(seatHoldDataAccessInterface.getAllSeatHoldsInTheShow(anyInt())).thenReturn(seatHolds);
        when(seatHoldDataAccessInterface.updateSeatReservationInShow(any(SeatHold.class)))
                .thenReturn(Boolean.TRUE);
    }

    @Test
    public void getSeats_test() {
        // Test for getting available seats
        List<Seat> availableSeats = seatService.getSeats(1, SeatHoldStatus.AVAILABLE);
        Assert.assertEquals(availableSeats.size(), SHOW_ID);
        // Test for getting held seats
        List<Seat> heldSeats = seatService.getSeats(1, SeatHoldStatus.HELD);
        Assert.assertEquals(heldSeats.size(), 1);
        // Test for getting Reserved seats
        List<Seat> reservedSeats = seatService.getSeats(1, SeatHoldStatus.RESERVED);
        Assert.assertEquals(reservedSeats.size(), 1);

    }

    @Test
    public void holdSeats_test() {
        // only 1 seat is available , we we try to hold seats , seat hold shouln't happen
        UUID holdId = seatService.holdSeats(5, SHOW_ID, "aa@aaa.com");
        Assert.assertNull(holdId);

        // Trying to hold 1 seat , hold should be successful
        holdId = seatService.holdSeats(1, SHOW_ID, "aa@aaa.com");
        Assert.assertNotNull(holdId);

    }

    @Test
    public void temporaryLockProvider_test() {
        // only 1 seat is available , we we try to hold seats , seat hold shouln't happen
        TemporaryLockProvider temporaryLockProvider = new TemporaryLockProvider();
        List<Seat> availableSeats = new ArrayList<>();
        availableSeats.add(seat3);

        // Acquiring lock on seat3
        LockedSeats lockedSeats = temporaryLockProvider.acquireTemporaryLockOnSeats(SHOW_ID, 1, availableSeats);
        Assert.assertTrue(lockedSeats.getIsLockSuccessful());

        // since the seat is locked by other process, hold seats should fail
        UUID holdId = seatService.holdSeats(1, SHOW_ID, "aa@aaa.com");
        Assert.assertNull(holdId);

        // Releasing the lock
        List<UUID> holdSeatIds = new ArrayList<>();
        holdSeatIds.add(seat3.getId());
        temporaryLockProvider.releaseTemporaryLock(SHOW_ID, holdSeatIds);

        // Trying to hold 1 seat , hold should be successful
        holdId = seatService.holdSeats(1, SHOW_ID, "aa@aaa.com");
        Assert.assertNotNull(holdId);

    }

}
