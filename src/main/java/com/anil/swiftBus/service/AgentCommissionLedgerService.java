package com.anil.swiftBus.service;

import java.time.LocalDate;
import java.util.List;

import com.anil.swiftBus.dto.AgentCommissionLedgerDTO;
import com.anil.swiftBus.dto.AgentSummaryDTO;

public interface AgentCommissionLedgerService {
    AgentCommissionLedgerDTO save(AgentCommissionLedgerDTO ledgerDTO);
    AgentCommissionLedgerDTO update(AgentCommissionLedgerDTO ledgerDTO);
    void delete(Long ledgerId);
    AgentCommissionLedgerDTO findById(Long ledgerId);
    List<AgentCommissionLedgerDTO> findAll();
    List<AgentCommissionLedgerDTO> findByAgentId(Long agentId);
    AgentCommissionLedgerDTO findByBookingId(Long bookingId);
	Long findByCommissionAgentId(long long1);
	public List<AgentSummaryDTO> getAgentWiseSummaryByDate(LocalDate startDate, LocalDate endDate, Boolean settled);
}
