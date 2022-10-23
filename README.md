# Getting Started

### Reference Documentation

1. Generate Private key `openssl genrsa -out keypair.pem 2848`
2. Extract public key `openssl rsa -in keypair.pem -pubout -out public.pem`
3. Generate Encrypted Private Key `openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem`
(if we use keypair.pem as a private key the system will complain.  "The private key needs to be in a PEM encoded pkcs8 format")


* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

