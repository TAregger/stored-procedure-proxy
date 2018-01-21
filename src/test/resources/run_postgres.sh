#!/bin/bash
docker run --name jdbc-postgres \
 -e POSTGRES_PASSWORD= \
 -e POSTGRES_USER=$USER \
 -p 5432:5432 \
 -d postgres:10.1-alpine
