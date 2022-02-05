## 🚀 배포 방법

> Ubuntu 18.04 에서 검증한 방법입니다.

### 1. 프로젝트 클론

```shell
$ git clone https://github.com/f-lab-edu/fream.git
```

### 2. 필수 패키지 설치

```shell
$ cd fream/deployment/infra
$ ./000-install-packages.sh
```

### 3. 환경 변수 수정

`load-envs.sh` 파일 내용을 수정한다.

반드시 확인해야 할 환경 변수는 `BASE_DIR`, `MYSQL_ROOT_PASSWORD`, `MYSQL_USER`, `MYSQL_PASSWORD`, `IMAGE_REGISTRY`
이다.

```sh
#!/bin/bash

# nginx, mysql 설정 파일이 위치한 곳.
export BASE_DIR=/home/user/fream/deployment/infra/conf

export MYSQL_DATA_DIR=$BASE_DIR/mysql/data/
export MYSQL_CONF_DIR=$BASE_DIR/mysql/conf.d/

export NGINX_CONF_DIR=$BASE_DIR/nginx/

# mysql 인증 정보를 기입
export MYSQL_ROOT_PASSWORD=
export MYSQL_USER=
export MYSQL_PASSWORD=
export MYSQL_DATABASE=fream

export FREAM_NETWORK_NAME=fream

export FREAM_DIR=$BASE_DIR/fream

# fream 이미지를 가져올 레지스트리 주소
export IMAGE_REGISTRY=127.0.0.1:5000

export HEALTH_CHECK_POINT=/products
export PORT=8080
```

### 4. Nginx, MySQL 컨테이너 생성

```shell
$ ./010-setup-intra.sh
```

스크립트를 실행할 때 이전 단계에서 설정한 환경 변수들도 같이 적용된다. 

컨테이너는 `fream` 이라는 도커 네트워크에 속하게 된다. 

### 5. 앱 실행

```shell
$ ./020-deploy-fream.sh
```

해당 스크립트는 무중단 배포가 적용되어 있어 새로운 버전의 이미지가 생성될 때 스크립트를 실행해주면 자동으로 최신 버전의 
컨테이너로 교체된다.
