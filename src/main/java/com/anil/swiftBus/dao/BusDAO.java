package com.anil.swiftBus.dao;

import java.util.List;
import java.util.Optional;

import com.anil.swiftBus.entity.Bus;
import com.anil.swiftBus.entity.BusSeat;

public interface BusDAO {
    void save(Bus bus);
    void update(Bus bus);
    Optional<Bus> findById(Long id);
    List<Bus> findAll();
    void deleteById(Long id);
	void saveBusSeat(BusSeat seat);
	void commit();
	boolean existsSeatNumber(Long busId, String seatNumber);
	boolean existsByRegistrationNo(String registrationNo);
	
	boolean existsByRegistrationNoAndNotId(String registrationNo, Long busId);


	boolean existsSeatNumberOtherThanId(Long busId, String seatNumber, Long seatId);
	Optional<BusSeat> findBySeatId(Long busSeatId);
	List<Long> getBookedSeatIds(Long id);

}
