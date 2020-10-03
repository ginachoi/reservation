package com.cr.reservation.controller;

import com.cr.reservation.model.ReservationType;
import com.cr.reservation.service.ReservationService;
import com.cr.reservation.web.ReservationRequest;
import com.cr.reservation.web.ReservationResponseDTO;
import com.cr.reservation.web.TableSeatResponseDTO;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation/v1")
public class ReservationController {

  private static final Logger log = LogManager.getLogger(ReservationController.class);
  private ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping(path = "/tables", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<TableSeatResponseDTO> getTables() {
    return reservationService.getTableList();

  }

  @PostMapping(path = "/reservations")
  @ResponseStatus(HttpStatus.CREATED)
  public List<ReservationResponseDTO> makeReservations(
      @RequestBody ReservationRequest reservationRequest) {
    log.debug("customer name=\"{}\"", reservationRequest.getCutomerName());
    log.debug("reservation type=\"{}\"", reservationRequest.getReservationType());
    log.debug("reservation count=\"{}\"", reservationRequest.getReservations().size());
    return this.reservationService.makeReservations(reservationRequest);
  }

  @GetMapping(path = "/reservations")
  @ResponseStatus(HttpStatus.OK)
  public List<ReservationResponseDTO> getReservations(
      @RequestParam(name = "type") ReservationType reservationType,
      @RequestParam(name = "name", required = false) Optional<String> customerName) {
    log.debug("reservation type=\"{}\"", reservationType);
    log.debug("customer is-present=\"{}\"", customerName.isPresent());
    return this.reservationService.getReservations(reservationType, customerName);
  }
}
