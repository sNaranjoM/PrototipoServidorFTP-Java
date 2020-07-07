# ServerProyectoRedesEntrable1
En este repositorio se encuentra el servidor  del proyecto de redes (Entregable 1) Por Steven Naranjo Mora
El presente proyecto esta realizado  por el estudiante Steven Naranjo Mora individualmente . Fue elabora en lenguaje JAVA y utilizando SQL Server  para instanciar las bases de datos utilizados en el proyectoEl . El proyecto esta compuesto de dos subproyectos,  la parte servidor y la parte cliente. En este repositorio se aloja la parte servidor. La parte cliente se puede encontrar en el siguiente reposi  https://github.com/sNaranjoM/ClienteProyectoRedesEntrable1.  

DESCRIPCION  DE INSTALACION:  este reposit   almacena el proyecto servidor.  Desde el IDE  de preferenia se debe correr primeramente el proyecto servidor, seguidamente el proyecto cliente. Tanto cliente como servidor debe desplegar interfaz grafica.  El servidor permite visualisar cuales son los  usuarios disponibles  y  los archivos que almacenan. El cliente presenta una pantalla de login ,  y  una pantalla  de tranferencia de archivos. Para subir archivos  se uliza la parte  izquierda y para descargar la parte derecha de la ventana. En la wiki de este repositorio se explica como esta constrido el proyecto.  

Título: Servidor proyecto Redes y comunicaciones de datos, entregable 2. Dicho proyecto fue construido con el lenguaje de programación Java, Versiones de Java: 7.0, 8.0. Puede servir en sistemas operativos Windows y linux, incluso que continuación se le presentará la implementación en windows 10.
Para este proyecto se utilizó el IDE de desarrollo Netbeans, y el jdk requerido para la compilacion y ejecucion de proyecto en Java. Ambos los puede descargar del siguiente enlace. 
https://www.oracle.com/technetwork/es/java/javase/downloads/jdk-netbeans-jsp-3413139-esa.html. Este enlace descargargara un ejecutable el cual al darle click, instalara todo lo necesario para la utilización de este proyecto.  Para apoyarse en este proceso puede seguir el siguiente enlace que lo llevara a un tutorial para instalar el IDE Netbeans. https://www.youtube.com/watch?v=WFFuSAAjJaE

Además necesita la librería jdom.jar la cual se puede obtener de la siguiente pagina web http://www.jdom.org/downloads/, ademas  se necesita la librería sqljdbc.jar para la conexión con la bases de datos, está la puede conseguir del siguiente enlace: http://www.java2s.com/Code/Jar/s/Downloadsqljdbcjar.htm El proceso para incluir una libreria al proyecto es el siguiente: 
Descargar el .jar del los enlaces. 
Click derecho sobre  la carpeta “Libraries” en Netbeans, la cual desplegará las siguiente opciones


![](https://github.com/sNaranjoM/ServerProyectoIIRedes/blob/master/Libraries.png)



Para esta seleccionamos la opción addJAR/Folder. Nos abrirá el siguiente buscador de archivos:


![](https://github.com/sNaranjoM/ServerProyectoIIRedes/blob/master/jar_search.PNG)	




Por último escogemos el .jar que descargamos y le damos a la opción de aceptar. Está operacion no ligara las librerias utilizadas en  el proyecto. Para el actual no hace falta porque ya vienen incluidas. SIn embargo dependiendo de la versión de Netbean instalada cada vez que se abra el proyecto cliente requerirá que se aplique esta misma operación, omitiendo la parte de descargar el archivo, el cual se encuentra en la carpeta lib de la raíz del proyecto. 
