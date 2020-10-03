package com.cr.reservation.controller;

import com.cr.reservation.service.ReservationService;
import com.cr.reservation.web.ReservationRequest;
import com.cr.reservation.web.ReservationResponseDTO;
import com.cr.reservation.web.TableSeatResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation/v1")
public class ReservationController {
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
    public List<ReservationResponseDTO> makeReservation(@RequestBody ReservationRequest reservationRequest) {
        System.out.println("customer name" + reservationRequest.getCutomerName());
        System.out.println("reservation tye" + reservationRequest.getReservationType());
        System.out.println("number of reservations" + reservationRequest.getReservations().size());
        return this.reservationService.makeReservations(reservationRequest);
    }
}
