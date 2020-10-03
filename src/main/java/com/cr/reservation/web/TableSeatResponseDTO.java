package com.cr.reservation.web;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"table_id", "seats"})
@JsonInclude(JsonInclude.Include.ALWAYS)
public class TableSeatResponseDTO {
    @JsonProperty("table_id")
    private String tableId;

    @JsonProperty("seats")
    private List<SeatDTO> seats;

    @JsonCreator
    public TableSeatResponseDTO(
            @JsonProperty("table_id") String tableId,
            @JsonProperty("seats") List<SeatDTO> seats) {
        this.tableId = tableId;
        this.seats = seats;
    }
}
