package com.anil.swiftBus.ModelMapper;

import com.anil.swiftBus.dto.AgentCommissionLedgerDTO;
import com.anil.swiftBus.entity.AgentCommissionLedger;

public class AgentCommissionLedgerMapper {

    public static AgentCommissionLedgerDTO toDTO(AgentCommissionLedger ledger) {
        if (ledger == null) return null;

        AgentCommissionLedgerDTO dto = new AgentCommissionLedgerDTO();
        dto.setLedgerId(ledger.getLedgerId());
        dto.setBookingId(ledger.getBooking() != null ? ledger.getBooking().getBookingId() : null);
        dto.setAgentId(ledger.getAgent() != null ? ledger.getAgent().getId() : null);
        dto.setCommissionAmount(ledger.getCommissionAmount());
        dto.setSettled(ledger.isSettled());
        dto.setCreatedAt(ledger.getCreatedAt());
        dto.setUpdatedAt(ledger.getUpdatedAt());

        return dto;
    }
}
