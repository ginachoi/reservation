package com.cr.reservation.service;

import com.cr.reservation.exception.UnAuthorizedException;
import com.cr.reservation.model.Reservation;
import com.cr.reservation.model.ReservationType;
import com.cr.reservation.model.TableSeat;
import com.cr.reservation.repository.ReservationRepository;
import com.cr.reservation.repository.TableSeatRepository;
import com.cr.reservation.web.CancelRequest;
import com.cr.reservation.web.ReservationRequest;
import com.cr.reservation.web.ReservationResponseDTO;
import com.cr.reservation.web.SeatDTO;
import com.cr.reservation.web.TableSeatDTO;
import com.cr.reservation.web.TableSeatResponseDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

  private static final Logger log = LogManager.getLogger(ReservationService.class);
  private TableSeatRepository tableSeatRepository;
  private ReservationRepository reservationRepository;

  @Autowired
  public ReservationService(TableSeatRepository tableSeatRepository,
      ReservationRepository reservationRepository) {
    this.tableSeatRepository = tableSeatRepository;
    this.reservationRepository = reservationRepository;
  }

  public List<TableSeatResponseDTO> getTableList() {
    List<TableSeat> totalSeats = this.tableSeatRepository.findAll();
    Map<String, List<TableSeat>> map = totalSeats.stream()
        .collect(Collectors.groupingBy(TableSeat::getTableId));
    List<TableSeatResponseDTO> seatDTOList = new ArrayList<>();
    for (Map.Entry<String, List<TableSeat>> entry : map.entrySet()) {
      String tableId = entry.getKey();
      List<SeatDTO> seats = entry.getValue().stream()
          .map(ts -> new SeatDTO(ts.getSeatId(), !ts.isBooked())).collect(Collectors.toList());
      TableSeatResponseDTO seatDTO = new TableSeatResponseDTO(tableId, seats);
      seatDTOList.add(seatDTO);
    }
    return seatDTOList;
  }

  public List<ReservationResponseDTO> makeReservations(ReservationRequest reservationRequest) {
    List<TableSeat> seats = this.tableSeatRepository.findAll();
    List<TableSeat> emptySeats = seats.stream().filter(seat -> seat.isBooked() == false)
        .collect(Collectors.toList());
    validateRequest(reservationRequest, emptySeats.size());
    log.debug("number of empty seats is {}", seats.size());
    Map<String, List<TableSeat>> totalSeatsByTable = seats.stream()
        .collect(Collectors.groupingBy(TableSeat::getTableId));
    String customerName = reservationRequest.getCustomerName();
    ReservationType reservationType = reservationRequest.getReservationType();
    List<Reservation> reservations = new ArrayList<>();
    for (List<TableSeatDTO> list : reservationRequest.getReservations()) {
      Reservation reservation = new Reservation();
      reservation.setCustomerName(customerName);
      reservation.setReservationType(reservationType);
      List<TableSeat> tableSeats = new ArrayList<>();
      for (TableSeatDTO dto : list) {
        validateTableSeat(totalSeatsByTable, dto);
        TableSeat ts = this.tableSeatRepository
            .findByTableIdAndSeatId(dto.getTableId(), dto.getSeatId());
        ts.setBooked(true);
        tableSeats.add(ts);
      }
      reservation.setGuests(tableSeats);
      reservations.add(reservation);
    }
    List<Reservation> savedList = (List<Reservation>) reservationRepository.saveAll(reservations);
    return convertToReservationResponseDTOS(savedList);
  }

  public List<ReservationResponseDTO> getReservations(ReservationType type,
      Optional<String> customerName) {
    List<Reservation> reservations = new ArrayList<>();

    if (ReservationType.OWNER.equals(type)) {
      // If the owner requested list of reservations then return all reservations
      reservations = this.reservationRepository.findAll();
    } else if (ReservationType.CUSTOMER.equals(type)) {
      // if the customer requested reservations list then return reservations belong to the customer only
      if (!customerName.isPresent() || StringUtils.isBlank(customerName.get())) {
        throw new IllegalArgumentException("Customer name is required.");
      }

      reservations = this.reservationRepository.findByCustomerName(customerName.get());

    }
    return convertToReservationResponseDTOS(reservations);
  }

  public void cancelReservations(CancelRequest cancelRequest) {
    List<Reservation> reservations = new ArrayList<>();
    for (Integer reservationId : cancelRequest.getReservations()) {
      Optional<Reservation> reservation = this.reservationRepository.findById(reservationId);
      checkReservationExist(reservationId, reservation);
      reservations.add(reservation.get());
    }
    ReservationType type = cancelRequest.getReservationType();
    String customerName = cancelRequest.getCustomerName();
    // The owner can delete any reservations
    if (ReservationType.OWNER.equals(cancelRequest.getReservationType())) {
      deleteReservations(reservations);
    } else if (ReservationType.CUSTOMER.equals(type)) {
      // Customers can delete only their own reservations
      checkAuthorizationForReservations(reservations, customerName);
      deleteReservations(reservations);
    }
  }

  private void deleteReservations(List<Reservation> reservations) {
    for (Reservation r : reservations) {
      for (TableSeat ts : r.getGuests()) {
        ts.setBooked(false);
        this.tableSeatRepository.save(ts);
      }
      this.reservationRepository.delete(r);
    }
  }


  private void checkReservationExist(int reservationId, Optional<Reservation> reservation) {
    if (!reservation.isPresent()) {
      String msg = String.format("Reservation with ID '%s' does not exist", reservationId);
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }
  }

  private void checkAuthorizationForReservations(List<Reservation> reservations,
      String customerName) {
    for (Reservation r : reservations) {
      if (!r.getCustomerName().equals(customerName)) {
        log.error(String
            .format("Customer with name '%s' try to cancel a reservation made by customer '%s'",
                customerName, r.getCustomerName()));
        throw new UnAuthorizedException("Not authorized");
      }
    }
  }


  private List<ReservationResponseDTO> convertToReservationResponseDTOS(
      List<Reservation> reservations) {
    List<ReservationResponseDTO> responseDTOS = new ArrayList<>();
    for (Reservation r : reservations) {
      List<TableSeatDTO> dtos = r.getGuests().stream()
          .map(g -> new TableSeatDTO(g.getTableId(), g.getSeatId())).collect(Collectors.toList());
      responseDTOS.add(
          new ReservationResponseDTO(r.getId(), r.getCustomerName(), r.getReservationType(), dtos));
    }
    return responseDTOS;
  }


  private void validateTableSeat(Map<String, List<TableSeat>> tableMap, TableSeatDTO tb) {
    if (tableMap.get(tb.getTableId()) == null) {
      String msg = String.format("Table with ID '%s' does not exit.", tb.getTableId());
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }
    List<TableSeat> tableSeats = tableMap.get(tb.getTableId());
    Optional<TableSeat> found = tableSeats.stream()
        .filter(s -> s.getSeatId().equals(tb.getSeatId())).findFirst();
    if (!found.isPresent()) {
      String msg = String
          .format("Table with ID '%s' and seat with ID '%s' does not exit.", tb.getTableId(),
              tb.getSeatId());
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }
    if (found.get().isBooked()) {
      String msg = String
          .format("Table with ID '%s' and seat ID '%s' is not available.", tb.getTableId(),
              tb.getSeatId());
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }
  }

  private void validateRequest(ReservationRequest reservationRequest, int numEmptySeats) {
    if (reservationRequest.getReservations().size() > 2) {
      throw new IllegalArgumentException("Cannot make more than two reservations");
    }
    int totalSeatRequested = reservationRequest.getReservations().stream()
        .flatMap(List::stream)
        .collect(Collectors.toList()).size();
    if (totalSeatRequested > 10) {
      String msg = "Cannot reserve more than 10 people per single reservation";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }
    log.debug("total seat count=\"{}\"", totalSeatRequested);
    if (totalSeatRequested > numEmptySeats) {
      throw new IllegalArgumentException("Not enough seats available");
    }
  }
}
