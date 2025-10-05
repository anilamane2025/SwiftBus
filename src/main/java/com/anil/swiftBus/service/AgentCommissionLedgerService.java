package com.anil.swiftBus.service;

import com.anil.swiftBus.dto.AgentCommissionLedgerDTO;
import java.util.List;

public interface AgentCommissionLedgerService {
    AgentCommissionLedgerDTO save(AgentCommissionLedgerDTO ledgerDTO);
    AgentCommissionLedgerDTO update(AgentCommissionLedgerDTO ledgerDTO);
    void delete(Long ledgerId);
    AgentCommissionLedgerDTO findById(Long ledgerId);
    List<AgentCommissionLedgerDTO> findAll();
    List<AgentCommissionLedgerDTO> findByAgentId(Long agentId);
    List<AgentCommissionLedgerDTO> findByBookingId(Long bookingId);
}
