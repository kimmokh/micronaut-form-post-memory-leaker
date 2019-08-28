#!/usr/bin/env bash

# Post form data 200 times, should not cause Netty leak alerts

for i in {1..200}
do
  curl -H "Content-Type: application/x-www-form-urlencoded"  --data "password=secret" -X POST http://localhost:8090/leak/create-form
done