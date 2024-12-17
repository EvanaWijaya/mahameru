# Mobile API Spec

## 1. Register User API

Public endpoint : POST /mobile/register

Request Body :
```json
{
  "username" : "username example",
  "email" : "email example",
  "password" : "password example"
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
  "error" : "Username atau password salah"
}
```

## 2. Login User API

Public endpoint : POST /mobile/login

Request Body :
```json
{
  "username" : "username example",
  "password" : "password example"
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