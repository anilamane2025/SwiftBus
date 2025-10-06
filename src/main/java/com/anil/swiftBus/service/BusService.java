package com.anil.swiftBus.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.anil.swiftBus.dto.BusDTO;
import com.anil.swiftBus.dto.TripSearchDTO;
import com.anil.swiftBus.exception.SeatValidationException;


public interface BusService {
    void addBus(BusDTO busDTO);
    void updateBus(Long id, BusDTO busDTO);
    BusDTO getBus(Long id);
    List<BusDTO> getAllBuses();
    void deleteBus(Long id);
    
    void saveBusWithSeats(BusDTO busDTO, BindingResult result) throws SeatValidationException;
	boolean existsByRegistrationNo(String registrationNo);
	void updateBusWithSeats(BusDTO busDTO, BindingResult result) throws SeatValidationException;
	boolean existsByRegistrationNoAndNotId(String registrationNo, Long busId);
	BusDTO getBusWithBookedStatus(BusDTO bus, TripSearchDTO trip);
}
