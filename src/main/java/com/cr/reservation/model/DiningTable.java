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
    @Column(name = "num_seat")
    private int numSeat;
    @Column(name = "booked")
    private boolean booked;
}
