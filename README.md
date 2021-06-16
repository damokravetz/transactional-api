![](https://github.com/damokravetz/transactional-api)

# Gateway Project

La API esta desarrollada en Java, usando Spring Boot framework.
    
## Autenticarse

### Request

`POST /auth`

    "username":"fuelpay",
    "password":"123qweasdzxc"
    
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

## Hacer una transacción

### Request

`POST /transaccion`

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
        
### Response

 `OK`

    {
        "data": {
            "message": "Succesful transcation",
            "statusCode": 200
        }
    }
 
 `Unauthorized`

    {
        "data": {
            "message": "Invalid transaction",
            "statusCode": 409
        }
    }

### Requerimiento
Requiere el header “Autorization” cuyo valor debe ser un token, ej “Bearer nd8293rh9whn230dj2d3j”
