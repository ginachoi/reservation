CREATE TABLE dining_table
(
  id int not null auto_increment,
  table_id varchar(4) not null,
  num_seat int not null,
  booked boolean default false,
  PRIMARY KEY (id)
);