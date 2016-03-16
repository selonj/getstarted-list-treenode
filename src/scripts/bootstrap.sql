create table hibernate_sequences(
  sequence_name varchar(100) not null,
  sequence_next_hi_value int not null
);

CREATE TABLE node (
  id        INT          NOT NULL PRIMARY KEY ,
  graph     VARCHAR(20)  NOT NULL,
  parent_id INT,
  path      VARCHAR(100)
);


