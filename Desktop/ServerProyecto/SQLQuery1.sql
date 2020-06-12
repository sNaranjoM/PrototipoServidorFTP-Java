create database b55008_proyectoRedes

use b55008_proyectoRedes

create table usuarios (
codigo			int identity(1,1)	NOT NULL constraint usuarios_PK PRIMARY KEY,
nombre			varchar(50) not null,
constrasena		varchar(50) not null,
urlCarpeta		varchar(100) not null,
)

select * from usuarios;

insert  usuarios values ('Steven','qwer','urlCarpeta');

select nombre, constrasena 
from usuarios
where nombre= 'Steven' ;


delete from usuarios where nombre='Steven';


