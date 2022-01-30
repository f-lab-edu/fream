#!/bin/bash

docker stop fream-nginx
docker stop fream-app-set1
docker stop fream-app-set2
docker stop fream-mysql

docker rm fream-nginx
docker rm fream-app-set1
docker rm fream-app-set2
docker rm fream-mysql
