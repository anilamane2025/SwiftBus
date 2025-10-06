package com.anil.swiftBus.cron;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.AgentCommissionLedgerDAO;
import com.anil.swiftBus.dao.BookingDAO;
import com.anil.swiftBus.dto.BookingTicketListDTO;
import com.anil.swiftBus.entity.AgentCommissionLedger;
import com.anil.swiftBus.entity.Booking;
import com.anil.swiftBus.enums.BookingStatus;

@Component
public class BookingStatusScheduler {

	private final BookingDAO bookingDAO;
	
	private final AgentCommissionLedgerDAO agentCommissionLedgerDAO;

	public BookingStatusScheduler(BookingDAO bookingDAO, AgentCommissionLedgerDAO agentCommissionLedgerDAO) {
		this.bookingDAO = bookingDAO;
		this.agentCommissionLedgerDAO = agentCommissionLedgerDAO;
	}



	@Scheduled(fixedRate = 300000) // Every 5 minutes
	@Transactional
	public void autoCompleteTrips() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

		List<BookingTicketListDTO> ticketList = bookingDAO.getAllBookingTicketsForAdmin();
		int completedCount = 0;

		for (BookingTicketListDTO dto : ticketList) {
			if (dto.getToArrivalTime() == null)
				continue;

			LocalDateTime arrivalTime;
			try {
				arrivalTime = LocalDateTime.parse(dto.getToArrivalTime(), formatter);
			} catch (Exception e) {
				continue; 
			}

			// Only mark completed if trip has finished
			if (arrivalTime.isBefore(now)) {
				Booking booking = bookingDAO.findById(dto.getBookingId());
				if (booking == null)
					continue;

				// Skip if already completed or cancelled
				if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.COMPLETED) {
					continue;
				}

				booking.setStatus(BookingStatus.COMPLETED);
				bookingDAO.update(booking);
				
				AgentCommissionLedger agentCommissionLedger= agentCommissionLedgerDAO.findByBookingId(booking.getBookingId());
				if(agentCommissionLedger == null)
					continue;
				
				agentCommissionLedger.setSettled(true);
				agentCommissionLedgerDAO.update(agentCommissionLedger);
				
				completedCount++;
			}
		}

		System.out.println("=== Auto completed " + completedCount + " bookings/trips at " + now);
	}
}
