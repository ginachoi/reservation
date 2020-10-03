package com.cr.reservation.web;

import com.cr.reservation.model.ReservationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationRequest {
    @JsonProperty("customer_name")
    private String cutomerName;

    @JsonProperty("reservation_type")
    private ReservationType reservationType;

    @JsonProperty("reservations")
    private List<List<TableSeatDTO>> reservations;
}
