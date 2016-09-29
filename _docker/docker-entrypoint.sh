#!/bin/sh

export JAVA="/usr/java/jre1.8.0_102/bin/java";
export JETTY_BASE="/root/dockress/server";

if [ ! $DOCKER_HOST ]; then
	export DOCKER_HOST="tcp://$(ip r | awk '/default/{print $3}'):2375";
	echo "use ${DOCKER_HOST} as docker server!"
fi

/root/dockress/jetty/bin/jetty.sh start

nginx -g "daemon off;"

