package com.cr.reservation.service;

import com.cr.reservation.model.Reservation;
import com.cr.reservation.model.ReservationType;
import com.cr.reservation.model.TableSeat;
import com.cr.reservation.repository.ReservationRepository;
import com.cr.reservation.repository.TableSeatRepository;
import com.cr.reservation.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private TableSeatRepository tableSeatRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(TableSeatRepository tableSeatRepository, ReservationRepository reservationRepository) {
        this.tableSeatRepository = tableSeatRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<TableSeatResponseDTO> getTableList() {
        List<TableSeat> totalSeats = this.tableSeatRepository.findAll();
        Map<String, List<TableSeat>> map = totalSeats.stream().collect(Collectors.groupingBy(TableSeat::getTableId));
        List<TableSeatResponseDTO> seatDTOList = new ArrayList<>();
        for (Map.Entry<String, List<TableSeat>> entry : map.entrySet()) {
            String tableId = entry.getKey();
            List<SeatDTO> seats = entry.getValue().stream().map(ts -> new SeatDTO(ts.getSeatId(), !ts.isBooked())).collect(Collectors.toList());
            TableSeatResponseDTO seatDTO = new TableSeatResponseDTO(tableId, seats);
            seatDTOList.add(seatDTO);
        }
        return seatDTOList;
    }

    public List<ReservationResponseDTO> makeReservations(ReservationRequest reservationRequest) {
        List<TableSeat> seats = this.tableSeatRepository.findAll();
        List<TableSeat> emptySeats = seats.stream().filter(seat -> seat.isBooked() == false).collect(Collectors.toList());
        validateRequest(reservationRequest, emptySeats.size());
        System.out.println("number of empty seats" + seats.size());
        Map<String, List<TableSeat>> totalSeatsByTable = seats.stream().collect(Collectors.groupingBy(TableSeat::getTableId));
        String customerName = reservationRequest.getCutomerName();
        ReservationType reservationType = reservationRequest.getReservationType();
        List<Reservation> reservations = new ArrayList<>();
        for (List<TableSeatDTO> list : reservationRequest.getReservations()) {
            Reservation reservation = new Reservation();
            reservation.setCustomerName(customerName);
            reservation.setReservationType(reservationType);
            List<TableSeat> tableSeats = new ArrayList<>();
            for (TableSeatDTO dto : list) {
                validateTableSeat(totalSeatsByTable, dto);
                TableSeat ts = this.tableSeatRepository.findByTableIdAndSeatId(dto.getTableId(), dto.getSeatId());
                ts.setBooked(true);
                tableSeats.add(ts);
            }
            reservation.setGuests(tableSeats);
            reservations.add(reservation);
        }
        List<Reservation> savedList = (List<Reservation>) reservationRepository.saveAll(reservations);
        List<ReservationResponseDTO> responseDTOS = new ArrayList<>();
        for (Reservation r : savedList) {
            List<TableSeatDTO> dtos = r.getGuests().stream().map(g -> new TableSeatDTO(g.getTableId(), g.getSeatId())).collect(Collectors.toList());
            responseDTOS.add(new ReservationResponseDTO(r.getId(), r.getCustomerName(), r.getReservationType(), dtos));
        }
        return responseDTOS;
    }


    private void validateTableSeat(Map<String, List<TableSeat>> tableMap, TableSeatDTO tb) {
        if (tableMap.get(tb.getTableId()) == null) {
            throw new IllegalArgumentException("Table with ID " + tb.getTableId() + " does not exit.");
        }
        List<TableSeat> tableSeats = tableMap.get(tb.getTableId());
        Optional<TableSeat> found = tableSeats.stream().filter(s -> s.getSeatId().equals(tb.getSeatId())).findFirst();
        if (!found.isPresent()) {
            throw new IllegalArgumentException("Table with ID " + tb.getTableId() + " and seat ID " + tb.getSeatId() + " does not exit.");
        }
        if (found.get().isBooked()) {
            throw new IllegalArgumentException("Table with ID " + tb.getTableId() + " and seat ID " + tb.getSeatId() + " is not available.");
        }
    }

    private void validateRequest(ReservationRequest reservationRequest, int numEmptySeats) {
        if (reservationRequest.getReservations().size() > 2) {
            throw new IllegalArgumentException("Cannot make more than two reservations");
        }
        for (List<TableSeatDTO> list : reservationRequest.getReservations()) {
            if (list.size() > 10) {
                throw new IllegalArgumentException("Cannot accept more than 10 people per single reservation");
            }
        }
        int totalSeatRequested = reservationRequest.getReservations().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList()).size();
        System.out.println("total seat requested: " + totalSeatRequested);
        if (totalSeatRequested > numEmptySeats) {
            throw new IllegalArgumentException("Not enough seats available");
        }
    }
}
