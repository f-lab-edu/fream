#!/bin/sh

export BASE_DIR=/home/user/fream-conf

export MYSQL_DATA_DIR=$BASE_DIR/mysql/data/
export MYSQL_CONF_DIR=$BASE_DIR/mysql/conf.d/

export NGINX_CONF_DIR=$BASE_DIR/nginx/

export FREAM_NETWORK_NAME=fream

export FREAM_DIR=$BASE_DIR/fream

export IMAGE_REGISTRY=172.0.0.1:5000

export NGINX_CONF_DIR=$BASE_DIR/nginx

export HEALTH_CHECK_POINT=/products
export PORT=8080
