package com.anil.swiftBus.dao;

import java.time.LocalDate;
import java.util.List;

import com.anil.swiftBus.entity.AgentCommissionLedger;

public interface AgentCommissionLedgerDAO {
    AgentCommissionLedger save(AgentCommissionLedger ledger);
    AgentCommissionLedger update(AgentCommissionLedger ledger);
    void delete(Long ledgerId);
    AgentCommissionLedger findById(Long ledgerId);
    List<AgentCommissionLedger> findAll();
    List<AgentCommissionLedger> findByAgentId(Long agentId);
    AgentCommissionLedger findByBookingId(Long bookingId);
	List<Object[]> getAgentWiseSummaryByDate(LocalDate startDate, LocalDate endDate, Boolean settled);
}
