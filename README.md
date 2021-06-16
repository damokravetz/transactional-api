![](https://github.com/damokravetz/transactional-api)

# Gateway Project

La API esta desarrollada en Java, usando Spring Boot framework.
    
## Autenticarse

### Request

`POST /auth`
 
     {
        "username":"test",
        "password":"pass"
     }
 
### Response

`OK`

     {
         "data": {
             "message": "Authorized",
             "statusCode": 200,
             "token": "Bearer token"
         }
     }
 
`Unauthorized`

     {
         "data": {
             "message": "Unauthorized",
                "statusCode": 401
         }
     }
     
### Funcionamiento
     
1.	Controlador recibe estos dos parámetros a través del enpoint “/auth”.
2.	Llama al servicio “AccountService” pasando los dos parámetros 
3.	El servicio hashea la contraseña con md5.
4.	Compara el usuario y su contraseña hasheada contra la capa de repositorio/ la base de datos.
5.	Si el usuario y la contraseña son correctos, el “AccountService” llama al servicio de autorización y le pasa el nombre de usuario.
6.	El servicio de autorización crea un token para el usuario y lo devuelve.
7.	El “AccountService” devuelve el usuario con el token al controlador.
8.	El controlador devuelve el token en la respuesta.

## Hacer una transacción

### Request

`POST /transaccion`

     Authorization: Bearer token

     {
         "idTransaction" : "12546563",
         "date": "01/06/2021",
         "amount": 1500.0,
         "creditCard": {
             "verificationCode": "312",
             "expirationDate": "11/25",
             "name": "test",
             "number": "4200145601473690",
             "type": "visa"
             },
         "user": {
             "dni": "20316161",
             "name": "test"
             },
         "commerce": {
             "cuit": "20335554441",
             "name": "test"
             }
     }
  
### Response

 `OK`

    {
        "data": {
            "message": "Succesful transcation",
            "statusCode": 200
        }
    }
 
 `Invalid`

    {
        "data": {
            "message": "Invalid transaction",
            "statusCode": 409
        }
    }
 
 `Expired Credit Card`

    {
        "data": {
            "message": "Credit card expired",
            "statusCode": 409
        }
    }
    
### Funcionamiento    
    
1.	El filtro de autorización JWT chequea la existencia de un token en el request y lo valida.
2.	Si no detecta ningún token o detecta un token invalido devuelve una respuesta http 403 forbbiden.
3.	Si el token es valido permite el request.
4.	El controlador recibe la información a través del endpoint /transaction.
5.	Llama al servicio de transacciones que se encarga de validar la transacción.
6.	El servicio se fija:
a.	Que exista el usuario.
b.	Que exista el comercio.
c.	Que exista la tarjeta.
d.	Que coincidan los datos de la tarjeta y la fecha de expiración sea válida.
7.	Una vez que chequea estos datos impacta la transacción en base de datos y devuelve una respuesta con código 200.

## Crear usuario

### Request

`POST /user/create`
 
     {
         "name": "test",
         "dni": "20316161"
     }
 
### Response

`OK`

     {
         "data": {
             "message": "User created succesfully",
             "statusCode": 201
         }
     }
 
`Already exists`

     {
         "data": {
             "message": "User already exists",
             "statusCode": 409
         }
     }
 
## Crear comercio

### Request

`POST /commerce/create`
 
     {
        "cuit": "20335554441",
        "name": "test"
     }
 
### Response

`OK`

     {
         "data": {
             "message": "Commerce created succesfully",
             "statusCode": 201
         }
     }
 
`Already exists`

     {
         "data": {
             "message": "Commerce already exists",
             "statusCode": 409
         }
     }
 
## Crear tarjeta

### Request

`POST /creditcard/create`
 
     {
        "verificationCode": "312",
        "expirationDate": "11/25",
        "name": "test",
        "number": "4200145601473690",
        "type": "visa"
     }
 
### Response

`OK`

     {
         "data": {
             "message": "CreditCard created succesfully",
             "statusCode": 201
         }
     }
 
`Already exists`

     {
         "data": {
             "message": "CreditCard already exists",
             "statusCode": 409
         }
     }
 
## Crear cuenta

### Request

`POST /account/create`
 
     {
        "cuit": "20284627461",
        "razonSocial": "test",
        "username": "test",
        "password": "pass"
     }
 
### Response

`OK`

     {
         "data": {
             "message": "Account created succesfully",
             "statusCode": 201
         }
     }
 
`Already exists`

     {
         "data": {
             "message": "Account already exists",
             "statusCode": 409
         }
     }
