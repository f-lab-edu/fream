#!/bin/bash

set -e

## load environment variables
. ./load-envs.sh

mkdir -p $MYSQL_DATA_DIR
mkdir -p $MYSQL_CONF_DIR

mkdir -p $NGINX_CONF_DIR

docker network create fream || true

docker run -d --name fream-mysql --network $FREAM_NETWORK_NAME                              \
        --restart always -v $MYSQL_CONF_DIR:/etc/mysql/conf.d/                              \
        -v $MYSQL_DATA_DIR:/var/lib/mysql/ -p 3306:3306                                     \
        -e TZ=Asia/Seoul -e MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD                        \
        -e MYSQL_USER=$MYSQL_USER -e MYSQL_PASSWORD=$MYSQL_PASSWORD                         \
        -e MYSQL_DATABASE=$MYSQL_DATABASE                                                   \
        mysql:8                                                                             \
        mysqld --default-authentication-plugin=mysql_native_password --ngram-token-size=2

docker run -d --name fream-nginx --network $FREAM_NETWORK_NAME \
        --restart always -v $NGINX_CONF_DIR:/etc/nginx/conf.d/ \
        -p 18080:18080 -p 80:80 -e TZ=Asia/Seoul               \
        nginx:latest
