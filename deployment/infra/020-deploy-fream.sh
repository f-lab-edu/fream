#!/bin/bash

find_current_active_set () {
	CONTAINER_NUMBERS=$(docker ps --filter "name=fream-app-set1" --format "{{.Names}}" | wc -l)

	if [ $CONTAINER_NUMBERS -gt 0 ]
	then
		NEXT_ACTIVE_SET=fream-app-set2
		NEXT_ACTIVE_PORT=8082
		LAST_ACTIVE_SET=fream-app-set1
	else
		NEXT_ACTIVE_SET=fream-app-set1
		NEXT_ACTIVE_PORT=8081
		LAST_ACTIVE_SET=fream-app-set2
	fi
}

try_to_remove_container() {
	docker stop $1 || true
	docker rm $1 || true
}

############################# START ###################################

## load environment variables
. ./load-envs.sh

## Define variables
FREAM_APP_VERSION=$1

if [ -z $FREAM_APP_VERSION ]
then
	echo "Usage: ./020-deploy-fream.sh [fream_version]"
	echo ""
        echo "Example: ./020-deploy-fream.sh 1.0.1"
	exit 1
fi


mkdir -p $FREAM_DIR

find_current_active_set

echo $NEXT_ACTIVE_SET

## Run latest application
docker run -d --name $NEXT_ACTIVE_SET -p $NEXT_ACTIVE_PORT:$NEXT_ACTIVE_PORT \
	--network fream -e SERVER_PORT=$NEXT_ACTIVE_PORT                           \
	-e SPRING_PROFILES_ACTIVE=prod -e FREAM_DB_URL=fream-mysql                 \
	-e FREAM_DB_PORT=3306 -e FREAM_DB_SCHEME=$MYSQL_DATABASE                   \
	-e FREAM_DB_USERNAME=$MYSQL_USER -e FREAM_DB_PASSWORD=$MYSQL_PASSWORD      \
	-e TZ=Asia/Seoul $IMAGE_REGISTRY/fream:$FREAM_APP_VERSION

## Referenced by https://jojoldu.tistory.com/267
for count in {1..10}
do
	echo "try to run health check"
	status_code=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:$NEXT_ACTIVE_PORT$HEALTH_CHECK_POINT)

	if [ $status_code -ge 200 ] && [ $status_code -lt 300 ]
	then
		break
	fi

	if [ $count -eq 10 ]
	then
		echo "Fail to Health checking"
		echo "Remove new containers"
		try_to_remove_container $NEXT_ACTIVE_SET
		exit 2
	fi

	echo "Retry Health Check after 5 seconds"
	sleep 10
done

echo "set \$service_url http://${NEXT_ACTIVE_SET}:${NEXT_ACTIVE_PORT};" | sudo tee $NGINX_CONF_DIR/service-url.inc

echo "Reload nginx"
docker exec fream-nginx nginx -s reload

echo "Remove previous container"
try_to_remove_container $LAST_ACTIVE_SET
