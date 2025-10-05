package com.anil.swiftBus.serviceImpl;

import com.anil.swiftBus.dao.AgentCommissionLedgerDAO;
import com.anil.swiftBus.dao.BookingDAO;
import com.anil.swiftBus.dao.BookingTicketDAO;
import com.anil.swiftBus.dao.BusDAO;
import com.anil.swiftBus.dao.FareSegmentDAO;
import com.anil.swiftBus.dao.PaymentDAO;
import com.anil.swiftBus.dao.TripDAO;
import com.anil.swiftBus.dao.UserDAO;
import com.anil.swiftBus.dto.BookingDTO;
import com.anil.swiftBus.dto.BookingRequestDTO;
import com.anil.swiftBus.dto.BookingResponseDTO;
import com.anil.swiftBus.dto.BusSeatDTO;
import com.anil.swiftBus.entity.AgentCommissionLedger;
import com.anil.swiftBus.entity.AgentCommissionRule;
import com.anil.swiftBus.entity.Booking;
import com.anil.swiftBus.entity.BookingTicket;
import com.anil.swiftBus.entity.Bus;
import com.anil.swiftBus.entity.BusSeat;
import com.anil.swiftBus.entity.FareSegment;
import com.anil.swiftBus.entity.Payment;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.entity.RouteStopPoint;
import com.anil.swiftBus.entity.Trip;
import com.anil.swiftBus.entity.User;
import com.anil.swiftBus.enums.BookingStatus;
import com.anil.swiftBus.enums.BookingTicketStatus;
import com.anil.swiftBus.enums.CommissionType;
import com.anil.swiftBus.enums.PaymentMethod;
import com.anil.swiftBus.enums.PaymentStatus;
import com.anil.swiftBus.enums.PaymentTxnStatus;
import com.anil.swiftBus.ModelMapper.*;
import com.anil.swiftBus.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

	private final BookingDAO bookingDAO;
    private final BookingTicketDAO ticketDAO;
    private final PaymentDAO paymentDAO;
    private final AgentCommissionLedgerDAO commissionDAO;
    private final TripDAO tripDAO;
    private final UserDAO userDAO;
    private final BusDAO busDAO;
    private final FareSegmentDAO fareSegmentDAO;

    public BookingServiceImpl(BookingDAO bookingDAO, BookingTicketDAO ticketDAO,
                              PaymentDAO paymentDAO, AgentCommissionLedgerDAO commissionDAO,
                              TripDAO tripDAO,UserDAO userDAO , BusDAO busDAO,FareSegmentDAO fareSegmentDAO) {
        this.bookingDAO = bookingDAO;
        this.ticketDAO = ticketDAO;
        this.paymentDAO = paymentDAO;
        this.commissionDAO = commissionDAO;
        this.tripDAO = tripDAO;
        this.userDAO = userDAO;
        this.busDAO = busDAO;
        this.fareSegmentDAO = fareSegmentDAO;
    }

    @Override
    public BookingDTO save(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setBookingTime(bookingDTO.getBookingTime());
        booking.setTotalAmount(bookingDTO.getTotalAmount());
        booking.setStatus(bookingDTO.getStatus());
        booking.setPaymentStatus(bookingDTO.getPaymentStatus());
        bookingDAO.save(booking);
        return BookingMapper.toDTO(booking);
    }

    @Override
    public BookingDTO update(BookingDTO bookingDTO) {
        Booking booking = bookingDAO.findById(bookingDTO.getBookingId());
        if (booking != null) {
            booking.setBookingTime(bookingDTO.getBookingTime());
            booking.setTotalAmount(bookingDTO.getTotalAmount());
            booking.setStatus(bookingDTO.getStatus());
            booking.setPaymentStatus(bookingDTO.getPaymentStatus());
            booking = bookingDAO.update(booking);
        }
        return BookingMapper.toDTO(booking);
    }

    @Override
    public void delete(Long bookingId) {
        bookingDAO.delete(bookingId);
    }

    @Override
    public BookingDTO findById(Long bookingId) {
        return BookingMapper.toDTO(bookingDAO.findById(bookingId));
    }

    @Override
    public List<BookingDTO> findAll() {
        return bookingDAO.findAll().stream()
                         .map(BookingMapper::toDTO)
                         .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponseDTO confirmBooking(BookingRequestDTO request) {
        BookingResponseDTO response = new BookingResponseDTO();
        try {
            // Validate trip
            Trip trip = tripDAO.findById(request.getTripId());
            if (trip == null)
                throw new RuntimeException("Trip not found");

            // Get passenger
            User passenger = userDAO.findById(request.getPassengerId())
                    .orElseThrow(() -> new RuntimeException("Passenger not found"));

            // Get optional agent
            User agent = null;
            if (request.getAgentId() != null) {
                agent = userDAO.findById(request.getAgentId())
                        .orElseThrow(() -> new RuntimeException("Agent not found"));
            }

            // Create booking
            Booking booking = new Booking();
            booking.setTrip(trip);
            booking.setPassenger(passenger);
            booking.setAgent(agent);
            booking.setBookingTime(LocalDateTime.now());
            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setPaymentStatus(PaymentStatus.PENDING);
            booking.setCreatedAt(LocalDateTime.now());
            booking.setUpdatedAt(LocalDateTime.now());

            List<BookingTicket> tickets = new ArrayList<>();
            BigDecimal totalAmount = BigDecimal.ZERO;

            // Fetch bus & seats
            Bus bus = busDAO.findById(request.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + request.getBusId()));
            
            List<BusSeat> busSeats = bus.getSeats();
    	    
            if (busSeats == null || busSeats.isEmpty())
                throw new RuntimeException("No seats found for this bus");

            // Get route stop and points
            RouteStop fromStop = trip.getRoute().getStops().stream()
            		.filter(RouteStop::isEnabled)
                    .filter(rs -> rs.getStopName().equalsIgnoreCase(request.getFromName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("From stop not found"));

            RouteStop toStop = trip.getRoute().getStops().stream()
            		.filter(RouteStop::isEnabled)
                    .filter(rs -> rs.getStopName().equalsIgnoreCase(request.getToName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("To stop not found"));

            RouteStopPoint pickupPoint = fromStop.getStopPoints().stream()
            		.filter(RouteStopPoint::isEnabled)
                    .filter(p -> Objects.equals(p.getRouteStopPointId(), request.getPickupPoint()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Pickup point not found"));

            RouteStopPoint dropPoint = toStop.getStopPoints().stream()
            		.filter(RouteStopPoint::isEnabled)
                    .filter(p -> Objects.equals(p.getRouteStopPointId(), request.getDropPoint()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Drop point not found"));
            
            FareSegment fareSegment = fareSegmentDAO.findById(request.getFareSegmentId());
            if (fareSegment == null)
                throw new RuntimeException("No Price found for this fare");

            // Create booking tickets
            for (String seatIdStr : request.getSelectedSeats()) {
                Long seatId = Long.valueOf(seatIdStr);

                BusSeat seat = busSeats.stream()
                        .filter(bs -> bs.getBusSeatId().equals(seatId))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));

                // prevent double booking:
                List<Long> bookedSeatIds = busDAO.getBookedSeatIds(request.getTripId());
                if (bookedSeatIds.contains(seat.getBusSeatId())) throw new RuntimeException("Seat already booked: " + seat.getSeatNumber());

                BookingTicket ticket = new BookingTicket();
                ticket.setBooking(booking);
                ticket.setBusSeat(seat);
                ticket.setFromRouteStop(fromStop);
                ticket.setToRouteStop(toStop);
                ticket.setFromRouteStopPoint(pickupPoint);
                ticket.setToRouteStopPoint(dropPoint);
                ticket.setPassengerName(request.getPassengerName());
                ticket.setPassengerAge(Long.valueOf(request.getPassengerAge()));
                ticket.setPassengerGender(request.getGender());
                ticket.setFarePaid(fareSegment.getFareAmount());
                ticket.setBookingTicketStatus(BookingTicketStatus.ACTIVE);
                ticket.setCreatedAt(LocalDateTime.now());
                ticket.setUpdatedAt(LocalDateTime.now());

                tickets.add(ticket);
                totalAmount = totalAmount.add(fareSegment.getFareAmount());
            }

            booking.setTickets(tickets);
            booking.setTotalAmount(totalAmount);

            // Save booking
            bookingDAO.save(booking);

            // Create payment record
            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setAmount(totalAmount);
            String txId = String.valueOf((int)(Math.random() * 900000) + 100000);
            payment.setGatewayTransactionId(txId+"_S"+LocalDateTime.now().toString());
            payment.setPaymentMethod(PaymentMethod.CASH); // or CASH,UPI, NETBANKING, etc.
            payment.setStatus(PaymentTxnStatus.SUCCESS);
            payment.setCreatedAt(LocalDateTime.now());
            payment.setUpdatedAt(LocalDateTime.now());
            paymentDAO.save(payment);

            // Agent commission (if applicable)
            if (agent != null) {
            	AgentCommissionRule agentCommissionRule =  userDAO.findCommissionRuleByAgentId(agent.getId());
            	BigDecimal commissionRule = agentCommissionRule.getCommissionValue();
            	BigDecimal commissionAmount = null;
            	if (agentCommissionRule.getCommissionType() == CommissionType.PERCENTAGE) {
            	    commissionAmount = totalAmount.multiply(commissionRule).divide(new BigDecimal("100"));
            	} else {
            	    // If it's a fixed amount, use commissionRule directly
            	    commissionAmount = commissionRule;
            	}
                AgentCommissionLedger ledger = new AgentCommissionLedger();
                ledger.setBooking(booking);
                ledger.setAgent(agent);
                ledger.setCommissionAmount(commissionAmount);
                ledger.setSettled(false);
                ledger.setCreatedAt(LocalDateTime.now());
                commissionDAO.save(ledger);
            }

            // Build response
            response.setBookingId(booking.getBookingId());
            response.setStatus("SUCCESS");
            response.setPaymentStatus(booking.getPaymentStatus().name());
            response.setMessage("Booking confirmed successfully");
            response.setTotalAmount(totalAmount);
            response.setBookedSeats(
                    tickets.stream()
                            .map(t -> t.getBusSeat().getSeatNumber())
                            .collect(Collectors.toList())
            );

        } catch (Exception e) {
            throw new RuntimeException("Booking failed: " + e.getMessage(), e);
        }
        return response;
    }

}
