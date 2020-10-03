CREATE TABLE IF NOT EXISTS table_seat
(
  id int not null auto_increment,
  table_id varchar(4) not null,
  seat_id varchar(4) not null,
  booked boolean default false,
  unique (table_id, seat_id),
  primary key (id)
);

CREATE TABLE IF NOT EXISTS reservation
(
  id int not null auto_increment,
  reservation_type ENUM('OWNER', 'CUSTOMER'),
  customer_name varchar(20) not null,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS reservation_table_seat
(
  reservation_id int,
  table_seat_id int,
  foreign key (reservation_id) references reservation(id),
  foreign key (table_seat_id) references table_seat(id)
);
