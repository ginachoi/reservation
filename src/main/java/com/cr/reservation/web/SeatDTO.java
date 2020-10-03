package com.cr.reservation.web;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"seat_id", "availale"})
@JsonInclude(JsonInclude.Include.ALWAYS)
public class SeatDTO {

  @JsonProperty("seat_id")
  private String seatId;

  @JsonProperty("availale")
  private boolean availale;

  @JsonCreator
  public SeatDTO(
      @JsonProperty("seat_id") String seatId, @JsonProperty("availale") boolean available) {
    this.seatId = seatId;
    this.availale = available;
  }
}
