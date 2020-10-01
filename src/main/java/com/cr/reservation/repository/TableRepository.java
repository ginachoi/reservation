package com.cr.reservation.repository;

import com.cr.reservation.model.DiningTable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TableRepository extends CrudRepository<DiningTable, Long> {

    List<DiningTable> findByTableId(String tableId);
    List<DiningTable> findByTableIdAndSeatId(String tableId, String seatId);

}
