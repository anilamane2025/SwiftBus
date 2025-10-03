package com.anil.swiftBus.daoImpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.BusDAO;
import com.anil.swiftBus.entity.Bus;
import com.anil.swiftBus.entity.BusSeat;

@Repository
@Transactional
public class BusDAOImpl implements BusDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Bus bus) {
        em.persist(bus);
    }

    @Override
    public void update(Bus bus) {
        em.merge(bus);
    }

    @Override
    public Optional<Bus> findById(Long id) {
    	Bus bus =em.createQuery(
                "SELECT b FROM Bus b LEFT JOIN FETCH b.seats s WHERE b.id = :id and b.enabled = true ORDER BY b.busId, s.seatRow, s.seatCol", Bus.class)
                .setParameter("id", id)
                .getSingleResult();
        return Optional.ofNullable(bus);
    }

    @Override
    public List<Bus> findAll() {
    	 return em.createQuery(
    		        "SELECT DISTINCT b FROM Bus b LEFT JOIN FETCH b.seats s where b.enabled = true ORDER BY b.busId, s.seatRow, s.seatCol", 
    		        Bus.class
    		    ).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        
        Bus bus = em.find(Bus.class, id);
        if (bus == null) {
        	throw new IllegalArgumentException("Bus not found");
        }
        bus.setEnabled(false);
        em.merge(bus);
    }

	@Override
	public void saveBusSeat(BusSeat seat) {
		em.persist(seat);
	}

	@Override
	public boolean existsSeatNumber(Long busId, String seatNumber) {
		 String jpql = "SELECT COUNT(bs) FROM BusSeat bs WHERE bs.bus.busId = :busId AND bs.seatNumber = :seatNumber";
	        Long count = em.createQuery(jpql, Long.class)
	                       .setParameter("busId", busId)
	                       .setParameter("seatNumber", seatNumber)
	                       .getSingleResult();
	        return count > 0;
	}

	@Override
	public boolean existsByRegistrationNo(String registrationNo) {
		String jpql = "SELECT COUNT(b) FROM Bus b WHERE LOWER(b.registrationNo) = :registrationNo ";
        Long count = em.createQuery(jpql, Long.class)
                       .setParameter("registrationNo", registrationNo.toLowerCase())
                       .getSingleResult();
        return count > 0;
	}

	@Override
	public boolean existsByRegistrationNoAndNotId(String registrationNo, Long busId) {
		 String jpql = "SELECT COUNT(b) FROM Bus b WHERE LOWER(b.registrationNo) = :regNo AND b.busId <> :busId";
		    Long count = em.createQuery(jpql, Long.class)
		            .setParameter("regNo", registrationNo.toLowerCase())
		            .setParameter("busId", busId)
		            .getSingleResult();
		    return count > 0;
	}

	@Override
	public boolean existsSeatNumberOtherThanId(Long busId, String seatNumber, Long seatId) {
	    String jpql = "SELECT COUNT(s) FROM BusSeat s " +
                "WHERE s.bus.busId = :busId AND s.seatNumber = :seatNo AND s.busSeatId <> :seatId";
  Long count = em.createQuery(jpql, Long.class)
          .setParameter("busId", busId)
          .setParameter("seatNo", seatNumber)
          .setParameter("seatId", seatId != null ? seatId : -1L)
          .getSingleResult();
  return count > 0;
	}

	@Override
	public Optional<BusSeat> findBySeatId(Long busSeatId) {
		BusSeat busSeat =em.createQuery(
                "SELECT s FROM BusSeat s WHERE s.id = :id ", BusSeat.class)
                .setParameter("id", busSeatId)
                .getSingleResult();
        return Optional.ofNullable(busSeat);
	}

	@Override
	public void commit() {
		em.flush();
	}
}
