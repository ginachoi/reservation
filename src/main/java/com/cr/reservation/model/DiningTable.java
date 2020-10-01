package com.cr.reservation.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="dining_table")
public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "table_id")
    private String tableId;
    @Column(name = "seat_id")
    private String seatId;
    @Column(name = "booked")
    private boolean booked;
}
