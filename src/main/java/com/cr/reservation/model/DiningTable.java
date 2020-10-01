package com.cr.reservation.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="dining_table")
public class DiningTable {
    @Id
    private Integer id;
    private Integer seat;
}
