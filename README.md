![](https://github.com/damokravetz/transactional-api)

# Gateway Project

La API esta desarrollada en Java, usando Spring Boot framework.
    
## /auth

###Parámetros
    "username":"fuelpay",
    "password":"123qweasdzxc"

###Funcionamiento
1.	El Controlador recibe estos dos parámetros a través del enpoint “/auth”.
2.	Llama al servicio “AccountService” pasando los dos parámetros. 
3.	El servicio hashea la contraseña con md5.
4.	Compara el usuario y su contraseña hasheada contra la capa de repositorio/ la base de datos.
5.	Si el usuario y la contraseña son correctos, el “AccountService” llama al servicio de autorización y le pasa el nombre de usuario.
6.	El servicio de autorización crea un token para el usuario y lo devuelve.
7.	El “AccountService” devuelve el usuario con el token al controlador.
8.	El controlador guarda el token en los headers de autorización de la respuesta y la devuelve.

## /transfer

### Parámetros


    "idTransaction" : "12546563",
    "date": "01/06/2021",
    "amount": 1500.0,
    "creditCard": {
        "verificationCode": "312",
        "expirationDate": "11/25",
        "name": "Lucas Gonzales",
        "number": "4200145601473690",
        "type": "visa"
        },
    "user": {
        "dni": "20316161",
        "name": "Lucas Gonzales"
        },
    "commerce": {
        "cuit": "20335554441",
        "name": "Almacen don Tito"
        }

### Requerimiento
Requiere el header “Autorization” cuyo valor debe ser un token, ej “Bearer nd8293rh9whn230dj2d3j”

### Funcionamiento
1.	El controlador recibe la informacion a través del endpoint /transaction.
2.	Llama al servicio de transacciones que se encarga de validar la transacción.
3.	El servicio se fija:
a.	Que exista el usuario.
b.	Que exista el comercio.
c.	Que exista la tarjeta.
d.	Que coincidan los datos de la tarjeta y la fecha de expiración sea valida.
4.	Una vez que checkea estos datos impacta la transacción en base de datos.
5.	Devuelve la información de la transacción al controlador y el controlador lo devuelve como respuesta.
