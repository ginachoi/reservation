package com.cr.reservation.model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Data
@Entity
@Table(name="table_seat")
public class TableSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "table_id")
    private String tableId;
    @Column(name = "seat_id")
    private String seatId;
    @Column(name = "booked")
    private boolean booked;

    public TableSeat() {

    }
}
