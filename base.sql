/*
Created		02.10.2019
Modified		28.11.2019
Project		
Model		
Company		
Author		
Version		
Database		mySQL 5 
*/


Create table produkt (
	id_prod Int NOT NULL AUTO_INCREMENT,
	nazwa_prod Text NOT NULL,
	material Text NOT NULL,
	typ Text NOT NULL,
	cena Float NOT NULL,
	UNIQUE (id_prod),
 Primary Key (id_prod)) ENGINE = MyISAM;

Create table ilosc (
	id_ilosc Int NOT NULL,
	ilosc Int NOT NULL,
	id_alejka Int NOT NULL,
 Primary Key (id_ilosc)) ENGINE = MyISAM;

Create table alejka (
	id_alejka Int NOT NULL AUTO_INCREMENT,
	alejka Int NOT NULL,
	id_dostepnosc Int NOT NULL,
 Primary Key (id_alejka)) ENGINE = MyISAM;

Create table miejsce (
	id_miejsce Int NOT NULL AUTO_INCREMENT,
	miejsce Int NOT NULL,
	id_alejka Int NOT NULL,
	UNIQUE (id_miejsce),
 Primary Key (id_miejsce)) ENGINE = MyISAM;

Create table dostepnosc (
	id_dostepnosc Int NOT NULL AUTO_INCREMENT,
	dostepnosc Varchar(20) NOT NULL,
	id_prod Int NOT NULL,
	UNIQUE (id_dostepnosc),
 Primary Key (id_dostepnosc)) ENGINE = MyISAM;


Alter table dostepnosc add Foreign Key (id_prod) references produkt (id_prod) on delete  restrict on update  restrict;
Alter table miejsce add Foreign Key (id_alejka) references alejka (id_alejka) on delete  restrict on update  restrict;
Alter table ilosc add Foreign Key (id_alejka) references alejka (id_alejka) on delete  restrict on update  restrict;
Alter table alejka add Foreign Key (id_dostepnosc) references dostepnosc (id_dostepnosc) on delete  restrict on update  restrict;


