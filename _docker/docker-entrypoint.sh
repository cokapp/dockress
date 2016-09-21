#!/bin/sh

cd /root/dockress/jetty
bin/jetty.sh start 

nginx -g "daemon off;"

