package com.cr.reservation.repository;


import com.cr.reservation.model.Reservation;
import com.cr.reservation.model.TableSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

  List<Reservation> findByCustomerName(String tableId);

  List<Reservation> findAll();
}
