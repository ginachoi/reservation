CREATE TABLE IF NOT EXISTS dining_table
(
  id int not null auto_increment,
  table_id varchar(4) not null,
  seat_id varchar(4) not null,
  booked boolean default false,
  unique (table_id, seat_id),
  primary key (id)
);