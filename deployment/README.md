## π λ°°ν¬ λ°©λ²

> Ubuntu 18.04 μμ κ²μ¦ν λ°©λ²μλλ€.

### 1. νλ‘μ νΈ ν΄λ‘ 

```shell
$ git clone https://github.com/f-lab-edu/fream.git
```

### 2. νμ ν¨ν€μ§ μ€μΉ

```shell
$ cd fream/deployment/infra
$ ./000-install-packages.sh
```

### 3. νκ²½ λ³μ μμ 

`load-envs.sh` νμΌ λ΄μ©μ μμ νλ€.

λ°λμ νμΈν΄μΌ ν  νκ²½ λ³μλ `BASE_DIR`, `MYSQL_ROOT_PASSWORD`, `MYSQL_USER`, `MYSQL_PASSWORD`, `IMAGE_REGISTRY`
μ΄λ€.

```sh
#!/bin/bash

# nginx, mysql μ€μ  νμΌμ΄ μμΉν κ³³.
export BASE_DIR=/home/user/fream/deployment/infra/conf

export MYSQL_DATA_DIR=$BASE_DIR/mysql/data/
export MYSQL_CONF_DIR=$BASE_DIR/mysql/conf.d/

export NGINX_CONF_DIR=$BASE_DIR/nginx/

# mysql μΈμ¦ μ λ³΄λ₯Ό κΈ°μ
export MYSQL_ROOT_PASSWORD=
export MYSQL_USER=
export MYSQL_PASSWORD=
export MYSQL_DATABASE=fream

export FREAM_NETWORK_NAME=fream

export FREAM_DIR=$BASE_DIR/fream

# fream μ΄λ―Έμ§λ₯Ό κ°μ Έμ¬ λ μ§μ€νΈλ¦¬ μ£Όμ
export IMAGE_REGISTRY=127.0.0.1:5000

export HEALTH_CHECK_POINT=/products
export PORT=8080
```

### 4. Nginx, MySQL μ»¨νμ΄λ μμ±

```shell
$ ./010-setup-intra.sh
```

μ€ν¬λ¦½νΈλ₯Ό μ€νν  λ μ΄μ  λ¨κ³μμ μ€μ ν νκ²½ λ³μλ€λ κ°μ΄ μ μ©λλ€. 

μ»¨νμ΄λλ `fream` μ΄λΌλ λμ»€ λ€νΈμν¬μ μνκ² λλ€. 

### 5. μ± μ€ν

```shell
$ ./020-deploy-fream.sh
```

ν΄λΉ μ€ν¬λ¦½νΈλ λ¬΄μ€λ¨ λ°°ν¬κ° μ μ©λμ΄ μμ΄ μλ‘μ΄ λ²μ μ μ΄λ―Έμ§κ° μμ±λ  λ μ€ν¬λ¦½νΈλ₯Ό μ€νν΄μ£Όλ©΄ μλμΌλ‘ μ΅μ  λ²μ μ 
μ»¨νμ΄λλ‘ κ΅μ²΄λλ€.
