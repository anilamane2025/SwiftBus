package com.anil.swiftBus.daoImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.anil.swiftBus.dao.AgentCommissionLedgerDAO;
import com.anil.swiftBus.entity.AgentCommissionLedger;
import com.anil.swiftBus.enums.BookingStatus;

@Repository
public class AgentCommissionLedgerDAOImpl implements AgentCommissionLedgerDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public AgentCommissionLedger save(AgentCommissionLedger ledger) {
        em.persist(ledger);
        return ledger;
    }

    @Override
    public AgentCommissionLedger update(AgentCommissionLedger ledger) {
        return em.merge(ledger);
    }

    @Override
    public void delete(Long ledgerId) {
        AgentCommissionLedger ledger = findById(ledgerId);
        if (ledger != null) em.remove(ledger);
    }

    @Override
    public AgentCommissionLedger findById(Long ledgerId) {
        return em.find(AgentCommissionLedger.class, ledgerId);
    }

    @Override
    public List<AgentCommissionLedger> findAll() {
    	 String ql = "SELECT l FROM AgentCommissionLedger l "
                 + "JOIN FETCH l.agent a "
                 + "JOIN FETCH l.booking b "
                 + "ORDER BY l.createdAt DESC";
    	 return em.createQuery(ql, AgentCommissionLedger.class).getResultList();
    }

    @Override
    public List<AgentCommissionLedger> findByAgentId(Long agentId) {
        return em.createQuery("SELECT l FROM AgentCommissionLedger l JOIN FETCH l.agent a JOIN FETCH l.booking b WHERE l.agent.id = :agentId ORDER BY l.createdAt DESC ", AgentCommissionLedger.class)
                 .setParameter("agentId", agentId)
                 .getResultList();
    }

    @Override
    public AgentCommissionLedger findByBookingId(Long bookingId) {
    	try {
        return em.createQuery("SELECT l FROM AgentCommissionLedger l JOIN FETCH l.booking b WHERE b.bookingId = :bookingId", AgentCommissionLedger.class)
                 .setParameter("bookingId", bookingId)
                 .getSingleResult();
    	} catch (NoResultException e) {
	        return null;
	    }
    }

	@Override
	public List<Object[]> getAgentWiseSummaryByDate(LocalDate startDate, LocalDate endDate,Boolean settled) {
		String ql = "SELECT a.id, CONCAT(a.firstName, ' ', a.lastName) AS agentName, " +
                "COUNT(b.bookingId), SUM(l.commissionAmount) " +
                "FROM AgentCommissionLedger l " +
                "JOIN l.agent a " +
                "JOIN l.booking b " +
                "WHERE b.status IN :statuses " +
                "AND b.bookingTime BETWEEN :startDateTime AND :endDateTime " ;
		
		Boolean settledFilter = (settled != null) ? settled : false;

	    ql += "AND l.settled = :settled ";
		ql +=  "GROUP BY a.id, a.firstName, a.lastName " +
                "ORDER BY a.firstName ASC";

     return  em.createQuery(ql, Object[].class)
             .setParameter("statuses", Arrays.asList(BookingStatus.COMPLETED, BookingStatus.CONFIRMED))
             .setParameter("startDateTime", startDate.atStartOfDay())
             .setParameter("endDateTime", endDate.plusDays(1).atStartOfDay().minusSeconds(1))
     		.setParameter("settled", settledFilter)
     		.getResultList();
	}
}
