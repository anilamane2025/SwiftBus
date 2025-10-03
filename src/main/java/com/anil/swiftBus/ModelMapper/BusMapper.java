package com.anil.swiftBus.ModelMapper;

import java.util.stream.Collectors;

import com.anil.swiftBus.dto.BusDTO;
import com.anil.swiftBus.dto.BusSeatDTO;
import com.anil.swiftBus.entity.Bus;
import com.anil.swiftBus.entity.BusSeat;

public class BusMapper {

    public static BusDTO toDTO(Bus bus) {
        if (bus == null) return null;

        BusDTO dto = new BusDTO();
        dto.setBusId(bus.getBusId());
        dto.setRegistrationNo(bus.getRegistrationNo());
        dto.setBusName(bus.getBusName());
        dto.setBusType(bus.getBusType());
        dto.setTotalSeats(bus.getTotalSeats());
        dto.setSeatLayoutVersion(bus.getSeatLayoutVersion());
        dto.setCreatedAt(bus.getCreatedAt());
        dto.setEnabled(bus.isEnabled());

        if (bus.getSeats() != null) {
            dto.setSeats(bus.getSeats().stream().map(BusMapper::toSeatDTO).collect(Collectors.toList()));
        }

        return dto;
    }

    public static Bus toEntity(BusDTO dto) {
        if (dto == null) return null;

        Bus bus = new Bus();
        bus.setRegistrationNo(dto.getRegistrationNo());
        bus.setBusName(dto.getBusName());
        bus.setBusType(dto.getBusType());
        bus.setTotalSeats(dto.getTotalSeats());
        bus.setSeatLayoutVersion(dto.getSeatLayoutVersion());
        bus.setEnabled(dto.isEnabled());

        return bus;
    }

    private static BusSeatDTO toSeatDTO(BusSeat seat) {
        BusSeatDTO dto = new BusSeatDTO();
        dto.setBusSeatId(seat.getBusSeatId());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setSeatType(seat.getSeatType());
        dto.setSeatRow(seat.getSeatRow());
        dto.setSeatCol(seat.getSeatCol());
        dto.setExtraInfo(seat.getExtraInfo());
        return dto;
    }
}
