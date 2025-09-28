package com.anil.swiftBus.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.anil.swiftBus.dao.BusDAO;
import com.anil.swiftBus.dto.BusDTO;
import com.anil.swiftBus.dto.BusSeatDTO;
import com.anil.swiftBus.entity.Bus;
import com.anil.swiftBus.entity.BusSeat;
import com.anil.swiftBus.exception.SeatValidationException;
import com.anil.swiftBus.ModelMapper.BusMapper;
import com.anil.swiftBus.service.BusService;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusDAO busDAO;

    @Override
    public void addBus(BusDTO busDTO) {
        Bus bus = BusMapper.toEntity(busDTO);
        busDAO.save(bus);
    }

    @Override
    public void updateBus(Long id, BusDTO busDTO) {
        Bus bus = busDAO.findById(id).orElseThrow(() -> new RuntimeException("Bus not found"));
        bus.setBusName(busDTO.getBusName());
        bus.setRegistrationNo(busDTO.getRegistrationNo());
        bus.setBusType(busDTO.getBusType());
        bus.setTotalSeats(busDTO.getTotalSeats());
        bus.setSeatLayoutVersion(busDTO.getSeatLayoutVersion());
        
        busDAO.update(bus);
    }

    @Override
    public BusDTO getBus(Long id) {
        return busDAO.findById(id).map(BusMapper::toDTO).orElse(null);
    }

    @Override
    public List<BusDTO> getAllBuses() {
        return busDAO.findAll().stream().peek(bus -> bus.getSeats().size()).map(BusMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteBus(Long id) {
        busDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void saveBusWithSeats(BusDTO busDTO, BindingResult result) throws SeatValidationException {

        Bus bus;
        boolean isNewBus = busDTO.getBusId() == null;

        if (isNewBus) {
            bus = new Bus();
            bus.setCreatedAt(LocalDateTime.now());
            bus.setEnabled(true);
        } else {
            bus = busDAO.findById(busDTO.getBusId())
                        .orElseThrow(() -> new SeatValidationException("Bus not found"));
        }

        bus.setBusName(busDTO.getBusName());
        bus.setRegistrationNo(busDTO.getRegistrationNo());
        bus.setBusType(busDTO.getBusType());
        bus.setTotalSeats(busDTO.getTotalSeats());
        bus.setSeatLayoutVersion(busDTO.getSeatLayoutVersion());

        List<BusSeat> busSeats = new ArrayList<>();
        Set<String> seatNumbers = new HashSet<>();

        int index = 0;
        for (BusSeatDTO s : busDTO.getSeats()) {
            String seatNo = s.getSeatNumber().trim();

            if (seatNo.isEmpty()) {
                result.reject("seatError", "Seat validation failed. Seat number cannot be empty!");
            }

            // 2️⃣ Duplicate seat in form
            if (!seatNumbers.add(seatNo)) {
                    result.reject("seatError", "Seat validation failed. Check duplicate or empty seat numbers!");

            }

            // 3️⃣ Duplicate seat in database for existing bus
            if (!isNewBus && busDAO.existsSeatNumber(bus.getBusId(), seatNo)) {
                result.reject("seatError", "Seat validation failed. Seat number already exists in this bus!");
            }

            BusSeat seat = new BusSeat();
            seat.setBus(bus);
            seat.setSeatNumber(seatNo);
            seat.setSeatType(s.getSeatType());
            seat.setSeatRow(s.getSeatRow());
            seat.setSeatCol(s.getSeatCol());
            seat.setExtraInfo(s.getExtraInfo());

            busSeats.add(seat);
            index++;
        }

        // If there are validation errors, throw exception
        if (result.hasErrors()) {
            throw new SeatValidationException("Seat validation failed");
        }

        bus.setSeats(busSeats);
        busDAO.save(bus);
       
    }

	@Override
	public boolean existsByRegistrationNo(String registrationNo) {
		return busDAO.existsByRegistrationNo(registrationNo);
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void updateBusWithSeats(BusDTO busDTO, BindingResult result) throws SeatValidationException {
		Bus bus = busDAO.findById(busDTO.getBusId())
	            .orElseThrow(() -> new SeatValidationException("Bus not found"));

	    bus.setBusName(busDTO.getBusName());
	    bus.setRegistrationNo(busDTO.getRegistrationNo());
	  
	    Set<String> seatNumbers = new HashSet<>();

	    for (BusSeatDTO s : busDTO.getSeats()) {
	        String seatNo = s.getSeatNumber() != null ? s.getSeatNumber().trim() : "";

	        if (seatNo.isEmpty()) {
	            result.reject("seatError", "Seat number cannot be empty!");
	        }

	        if (!seatNumbers.add(seatNo)) {
	            result.reject("seatError", "Duplicate seat number in form: " + seatNo);
	        }

	        // check duplicate only if another seat with same number exists in DB
	        if (busDAO.existsSeatNumberOtherThanId(bus.getBusId(), seatNo, s.getBusSeatId())) {
	            result.reject("seatError", "Seat number already exists in this bus: " + seatNo);
	            throw new SeatValidationException("Seat number already exists in this bus: " + seatNo);
	        }

	        BusSeat seat;
	        if (s.getBusSeatId() != null) {
	            // Update existing seat
	            seat = busDAO.findBySeatId(s.getBusSeatId())
	                    .orElse(new BusSeat()); // fallback if not found
	        } else {
	            // New seat
	            seat = new BusSeat();
	            seat.setBus(bus);
	            bus.getSeats().add(seat);
	        }

	        seat.setSeatNumber(seatNo);
	        seat.setExtraInfo(s.getExtraInfo());

	    }

	    if (result.hasErrors()) {
	        throw new SeatValidationException("Seat validation failed");
	    }

		
	}

	@Override
	public boolean existsByRegistrationNoAndNotId(String registrationNo, Long busId) {
		return busDAO.existsByRegistrationNoAndNotId(registrationNo,busId);
	}

    
}
