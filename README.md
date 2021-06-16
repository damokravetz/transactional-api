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
