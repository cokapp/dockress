#!/bin/sh

if [ ! $DOCKER_HOST ]; then
	HOST_IP=$(/sbin/ip route|awk '/default/ { print $3 }');
	export DOCKER_HOST="tcp://${HOST_IP}:2375";
fi

cd /root/dockress/jetty
bin/jetty.sh start 

nginx -g "daemon off;"

