package com.anil.swiftBus.daoImpl;

import com.anil.swiftBus.dao.PaymentDAO;
import com.anil.swiftBus.entity.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PaymentDAOImpl implements PaymentDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Payment save(Payment payment) {
        em.persist(payment);
        return payment;
    }

    @Override
    public Payment update(Payment payment) {
        return em.merge(payment);
    }

    @Override
    public void delete(Long paymentId) {
        Payment p = findById(paymentId);
        if (p != null) em.remove(p);
    }

    @Override
    public Payment findById(Long paymentId) {
        return em.find(Payment.class, paymentId);
    }

    @Override
    public List<Payment> findAll() {
        return em.createQuery("SELECT p FROM Payment p", Payment.class).getResultList();
    }

    @Override
    public List<Payment> findByBookingId(Long bookingId) {
        return em.createQuery("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId", Payment.class)
                .setParameter("bookingId", bookingId)
                .getResultList();
    }
}
