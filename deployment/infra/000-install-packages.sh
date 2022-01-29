#!/bin/bash

# For Ubuntu 16.04

set -xe

sudo apt-get update
sudo apt-get install --no-install-recommends -y \
    git \
    curl \
    bc \
    docker.io
