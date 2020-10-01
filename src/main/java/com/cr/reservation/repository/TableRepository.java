package com.cr.reservation.repository;

import com.cr.reservation.model.DiningTable;
import org.springframework.data.repository.CrudRepository;

public interface TableRepository extends CrudRepository<DiningTable, Long> {

    DiningTable findByTableId(String tableId);

}
