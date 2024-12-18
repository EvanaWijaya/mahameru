# Web API Spec

## 1. Login Admin API

Public endpoint : POST /web/login

Request Body :
```json
{
  "email" : "admin@gmail.com",
  "password" : "admin123"
}
```

Response Body Success : 
```json
{
  "data" : {
    "token" : "unique-token"
  }
}
```

Response Body Error :
```json
{
  "error" : "Email atau password salah"
}
```

## 2. Add Tiket API

Protected endpoint : POST /web/tiket

Authorization : <token>

Request Body :
```json
{
  "name" : "tiket name example",
  "price" : "100.000",
  "amount" : "10",
  "description" : "description example",
}
```

Response Body Success : 
```json
{
  "data" : {
    "name" : "tiket name example",
    "price" : "100.000",
    "amount" : "10",
    "description" : "description example",
  }
}
```

Response Body Error :
```json
{
  "error" : "Could not add tiket"
}
```

## 3. Add Equipment API

Protected endpoint : POST /web/equipment

Authorization : <token>

Request Body :
```json
{
  "code" : "BKD01",
  "name" : "equipment name example",
  "price" : "100.000",
  "amount" : "10",
}
```

Response Body Success : 
```json
{
  "data" : {
    "code" : "BKD01",
    "name" : "equipment name example",
    "price" : "100.000",
    "amount" : "10",
  }
}
```

Response Body Error :
```json
{
  "error" : "Could not add equipment"
}
