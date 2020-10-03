package com.cr.reservation.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable
            (
                    name="reservation_table_seat",
                    joinColumns={ @JoinColumn(name="reservation_id", referencedColumnName="id", unique=true) },
                    inverseJoinColumns={ @JoinColumn(name="table_seat_id", referencedColumnName="id", unique=true) }
            )
    private List<TableSeat> guests;

    public Reservation() {

    }
}
