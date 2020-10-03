package com.cr.reservation.web;

import com.cr.reservation.model.ReservationType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationResponseDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("customer_name")
    private String customerName;
    @JsonProperty("reservation_type")
    private ReservationType reservationType;
    @JsonProperty("seats")
    private List<TableSeatDTO> guests;

    @JsonCreator
    public ReservationResponseDTO(@JsonProperty("id") int id, @JsonProperty("customer_name") String customerName,
                                  @JsonProperty("reservation_type") ReservationType reservationType,
                                  @JsonProperty("seats") List<TableSeatDTO> guests) {
        this.id = id;
        this.customerName = customerName;
        this.reservationType = reservationType;
        this.guests = guests;
    }
}
