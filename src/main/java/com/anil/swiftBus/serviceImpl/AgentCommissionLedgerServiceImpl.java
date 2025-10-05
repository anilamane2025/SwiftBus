package com.anil.swiftBus.serviceImpl;

import com.anil.swiftBus.dao.AgentCommissionLedgerDAO;
import com.anil.swiftBus.dto.AgentCommissionLedgerDTO;
import com.anil.swiftBus.entity.AgentCommissionLedger;
import com.anil.swiftBus.ModelMapper.AgentCommissionLedgerMapper;
import com.anil.swiftBus.service.AgentCommissionLedgerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AgentCommissionLedgerServiceImpl implements AgentCommissionLedgerService {

    private final AgentCommissionLedgerDAO ledgerDAO;

    public AgentCommissionLedgerServiceImpl(AgentCommissionLedgerDAO ledgerDAO) {
        this.ledgerDAO = ledgerDAO;
    }

    @Override
    public AgentCommissionLedgerDTO save(AgentCommissionLedgerDTO dto) {
        AgentCommissionLedger ledger = new AgentCommissionLedger();
        ledger.setBooking(dto.getBookingId() != null ? new com.anil.swiftBus.entity.Booking() {{ setBookingId(dto.getBookingId()); }} : null);
        ledger.setAgent(dto.getAgentId() != null ? new com.anil.swiftBus.entity.User() {{ setId(dto.getAgentId()); }} : null);
        ledger.setCommissionAmount(dto.getCommissionAmount());
        ledger.setSettled(dto.isSettled());
        ledgerDAO.save(ledger);
        return AgentCommissionLedgerMapper.toDTO(ledger);
    }

    @Override
    public AgentCommissionLedgerDTO update(AgentCommissionLedgerDTO dto) {
        AgentCommissionLedger ledger = ledgerDAO.findById(dto.getLedgerId());
        if (ledger != null) {
            ledger.setCommissionAmount(dto.getCommissionAmount());
            ledger.setSettled(dto.isSettled());
            ledger = ledgerDAO.update(ledger);
        }
        return AgentCommissionLedgerMapper.toDTO(ledger);
    }

    @Override
    public void delete(Long ledgerId) {
        ledgerDAO.delete(ledgerId);
    }

    @Override
    public AgentCommissionLedgerDTO findById(Long ledgerId) {
        return AgentCommissionLedgerMapper.toDTO(ledgerDAO.findById(ledgerId));
    }

    @Override
    public List<AgentCommissionLedgerDTO> findAll() {
        return ledgerDAO.findAll().stream()
                .map(AgentCommissionLedgerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgentCommissionLedgerDTO> findByAgentId(Long agentId) {
        return ledgerDAO.findByAgentId(agentId).stream()
                .map(AgentCommissionLedgerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgentCommissionLedgerDTO> findByBookingId(Long bookingId) {
        return ledgerDAO.findByBookingId(bookingId).stream()
                .map(AgentCommissionLedgerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
