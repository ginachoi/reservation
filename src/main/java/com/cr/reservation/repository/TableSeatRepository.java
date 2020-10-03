package com.cr.reservation.repository;

import com.cr.reservation.model.TableSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableSeatRepository extends CrudRepository<TableSeat, Integer> {

  List<TableSeat> findByTableId(String tableId);

  TableSeat findByTableIdAndSeatId(String tableId, String seatId);

  List<TableSeat> findByBooked(boolean booked);

  List<TableSeat> findAll();
}
