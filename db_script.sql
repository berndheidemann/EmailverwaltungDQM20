drop table Email;

create table Email(
                      id integer PRIMARY KEY AUTOINCREMENT,
                      vorname varchar,
                      nachname varchar,
                      email varchar
);

insert into Email (vorname, nachname, email) VALUES ('Max', 'Mustermann', 'a@b.de');
insert into Email (vorname, nachname, email) VALUES ('Müller', 'Wolfgang', 'wolfgang@weber.de');
insert into Email (vorname, nachname, email) VALUES ('Heidemann', 'Bernd', 'b.heidemann@schule.bremen.de');


select * from Email