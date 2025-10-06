package com.anil.swiftBus.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.ModelMapper.AgentCommissionLedgerMapper;
import com.anil.swiftBus.dao.AgentCommissionLedgerDAO;
import com.anil.swiftBus.dto.AgentCommissionLedgerDTO;
import com.anil.swiftBus.dto.AgentSummaryDTO;
import com.anil.swiftBus.entity.AgentCommissionLedger;
import com.anil.swiftBus.service.AgentCommissionLedgerService;

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
    public AgentCommissionLedgerDTO findByBookingId(Long bookingId) {
        return AgentCommissionLedgerMapper.toDTO(ledgerDAO.findByBookingId(bookingId));
    }

	@Override
	public Long findByCommissionAgentId(long agentId) {
		BigDecimal totalCommission = ledgerDAO.findByAgentId(agentId).stream()
		        .filter(AgentCommissionLedger::isSettled) 
		        .map(AgentCommissionLedger::getCommissionAmount) 
		        .filter(Objects::nonNull) 
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
		return totalCommission.longValue();
	}

	public List<AgentSummaryDTO> getAgentWiseSummaryByDate(LocalDate startDate, LocalDate endDate, Boolean settled) {
	    List<Object[]> results = ledgerDAO.getAgentWiseSummaryByDate(startDate, endDate, settled);
	    
	    return results.stream().map(r -> {
	        AgentSummaryDTO dto = new AgentSummaryDTO();
	        dto.setAgentId((Long) r[0]);
	        dto.setAgentName((String) r[1]);
	        dto.setTotalBookings((Long) r[2]);
	        dto.setTotalCommission((BigDecimal) r[3]);
	        return dto;
	    }).collect(Collectors.toList());
	}

}
