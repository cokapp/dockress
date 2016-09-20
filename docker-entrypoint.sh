#!/bin/sh

#run jetty
cd /root/workspace/jetty
bin/jetty start &

#run nginx
cd /root/workspace/nginx
nginx &