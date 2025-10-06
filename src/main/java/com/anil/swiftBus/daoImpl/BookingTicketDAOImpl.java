package com.anil.swiftBus.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.anil.swiftBus.dao.BookingTicketDAO;
import com.anil.swiftBus.entity.BookingTicket;

@Repository
public class BookingTicketDAOImpl implements BookingTicketDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public BookingTicket save(BookingTicket ticket) {
        em.persist(ticket);
        return ticket;
    }

    @Override
    public BookingTicket update(BookingTicket ticket) {
        return em.merge(ticket);
    }

    @Override
    public void delete(Long bookingTicketId) {
        BookingTicket t = findById(bookingTicketId);
        if (t != null) em.remove(t);
    }

    @Override
    public BookingTicket findById(Long bookingTicketId) {
        return em.find(BookingTicket.class, bookingTicketId);
    }

    @Override
    public List<BookingTicket> findAll() {
        return em.createQuery("SELECT t FROM BookingTicket t", BookingTicket.class).getResultList();
    }
}
