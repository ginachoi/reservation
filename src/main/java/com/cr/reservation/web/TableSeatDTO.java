package com.cr.reservation.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableSeatDTO {

    @JsonProperty("table_id")
    private String tableId;
    @JsonProperty("seat_id")
    private String seatId;

    @JsonCreator
    public TableSeatDTO(
            @JsonProperty("table_id") String tableId,
            @JsonProperty("seat_id") String seatId) {
        this.tableId = tableId;
        this.seatId = seatId;
    }
}
