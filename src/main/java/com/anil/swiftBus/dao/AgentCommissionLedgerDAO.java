package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.AgentCommissionLedger;
import java.util.List;

public interface AgentCommissionLedgerDAO {
    AgentCommissionLedger save(AgentCommissionLedger ledger);
    AgentCommissionLedger update(AgentCommissionLedger ledger);
    void delete(Long ledgerId);
    AgentCommissionLedger findById(Long ledgerId);
    List<AgentCommissionLedger> findAll();
    List<AgentCommissionLedger> findByAgentId(Long agentId);
    List<AgentCommissionLedger> findByBookingId(Long bookingId);
}
