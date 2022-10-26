# JWT token Authentication with RSA key

How to configure authentication in Spring boot application using JWT token and RSA private/public key.

## RSA key generations

Generate Private key, Extract public key and Generate Encrypted Private Key. <br/>
If we use keypair.pem as a private key the system will complain. "The private key needs to be in a PEM encoded pkcs8 format".

```
openssl genrsa -out keypair.pem 2848
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

## Run and test the App

(1) Run API `JwtApplication.java`

(2) Generate JWT `JwtTokenGenerator.test_token()`

(3) Call the API
```
GET http://localhost:8080/
Authorization:Bearer <jwt-token generated in step 1>
```
OR
```
curl http://localhost:8080/ -H "Authorization: Bearer <jwt-token>
```
You should see as response "Hello JWT!"

```
GET http://localhost:8080/details
Authorization:Bearer <jwt-token>
```

You should see the Public Key in the response body.

[Http calls here](./REST/api.http)


## Links
* [Youtube tutorial - Spring Security JWT](https://www.youtube.com/watch?v=KYNR5js2cXE)

