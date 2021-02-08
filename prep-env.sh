#!/usr/bin/env bash
set  -xeuo pipefail

sudo apt-get update
#sudo apt-get install -y  openjdk-11-jdk
sudo apt-get install -y  maven 

mvn --version

