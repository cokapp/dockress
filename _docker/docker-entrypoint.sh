#!/bin/sh

export JAVA="/usr/java/jre1.8.0_102/bin/java";
export JETTY_BASE="/root/dockress/server";
export DOCKER_CERT_PATH="/root/dockress/certs/";

#AUTO DOCKER_TLS_VERIFY
if [ "`ls -A $DOCKER_CERT_PATH`" != "" ]; then
	export DOCKER_TLS_VERIFY="1";
	echo "use \"${DOCKER_CERT_PATH}\" as docker cert path!"
fi

#AUTO DOCKER_HOST
if [ ! $DOCKER_HOST ]; then
	HOST_IP="$(ip r | awk '/default/{print $3}')";
    if [ "`ls -A $DOCKER_CERT_PATH`" = "" ]; then
        export DOCKER_HOST="tcp://${HOST_IP}:2375";
    else
        export DOCKER_HOST="tcp://${HOST_IP}:2376";
    fi
fi
echo "use \"${DOCKER_HOST}\" as docker server!"


/root/dockress/jetty/bin/jetty.sh start

nginx -g "daemon off;"

