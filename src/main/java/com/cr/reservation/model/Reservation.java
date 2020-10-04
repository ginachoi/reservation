package com.cr.reservation.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "customer_name")
  private String customerName;
  @Enumerated(EnumType.STRING)
  @Column(name = "reservation_type", columnDefinition = "ENUM('OWNER', 'CUSTOMER')")
  private ReservationType reservationType;


  @OneToMany(
      cascade = CascadeType.REFRESH,
      orphanRemoval = false
  )
  @JoinTable
      (
          name = "reservation_table_seat",
          joinColumns = {
              @JoinColumn(name = "reservation_id", referencedColumnName = "id", unique = true)},
          inverseJoinColumns = {
              @JoinColumn(name = "table_seat_id", referencedColumnName = "id", unique = true)}
      )
  private List<TableSeat> guests;

  public Reservation() {

  }
}
