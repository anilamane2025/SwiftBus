package com.anil.swiftBus.daoImpl;

import com.anil.swiftBus.dao.AgentCommissionLedgerDAO;
import com.anil.swiftBus.entity.AgentCommissionLedger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
        return em.createQuery("SELECT l FROM AgentCommissionLedger l", AgentCommissionLedger.class)
                 .getResultList();
    }

    @Override
    public List<AgentCommissionLedger> findByAgentId(Long agentId) {
        return em.createQuery("SELECT l FROM AgentCommissionLedger l WHERE l.agent.id = :agentId", AgentCommissionLedger.class)
                 .setParameter("agentId", agentId)
                 .getResultList();
    }

    @Override
    public List<AgentCommissionLedger> findByBookingId(Long bookingId) {
        return em.createQuery("SELECT l FROM AgentCommissionLedger l WHERE l.booking.bookingId = :bookingId", AgentCommissionLedger.class)
                 .setParameter("bookingId", bookingId)
                 .getResultList();
    }
}
