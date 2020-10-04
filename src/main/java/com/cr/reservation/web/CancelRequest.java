package com.cr.reservation.web;

import com.cr.reservation.model.ReservationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelRequest {
  @JsonProperty("customer_name")
  private String customerName;

  @JsonProperty("reservation_type")
  private ReservationType reservationType;

  @JsonProperty("ids")
  private List<Integer> reservations;
}
