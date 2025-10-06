package com.anil.swiftBus.ModelMapper;

import java.time.format.DateTimeFormatter;

import com.anil.swiftBus.dto.AgentCommissionLedgerDTO;
import com.anil.swiftBus.entity.AgentCommissionLedger;
import com.anil.swiftBus.entity.Booking;
import com.anil.swiftBus.entity.User;

public class AgentCommissionLedgerMapper {
	

    public static AgentCommissionLedgerDTO toDTO(AgentCommissionLedger ledger) {
        if (ledger == null) return null;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

        AgentCommissionLedgerDTO dto = new AgentCommissionLedgerDTO();
        dto.setLedgerId(ledger.getLedgerId());
        dto.setBookingId(ledger.getBooking() != null ? ledger.getBooking().getBookingId() : null);
        dto.setAgentId(ledger.getAgent() != null ? ledger.getAgent().getId() : null);
        dto.setCommissionAmount(ledger.getCommissionAmount());
        dto.setSettled(ledger.isSettled());
        dto.setCreatedAt(ledger.getCreatedAt());
        dto.setUpdatedAt(ledger.getUpdatedAt());

        Booking booking = ledger.getBooking();
        if (booking != null) {
            dto.setBookingId(booking.getBookingId());
            dto.setBookingTotalAmount(booking.getTotalAmount());
            dto.setBookingStatus(booking.getStatus().name());
            dto.setPaymentStatus(booking.getPaymentStatus().name());
            dto.setBookedAt(booking.getBookingTime());
            dto.setBookedAtFormat(booking.getBookingTime().format(formatter));
        }

        User agent = ledger.getAgent();
        if (agent != null) {
            dto.setAgentId(agent.getId());
            dto.setAgentName(agent.getFirstName() + " " + agent.getLastName());
        }
        return dto;
    }
}
