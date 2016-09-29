FROM centos:7
MAINTAINER dev@cokapp.com

ENV DOCKER_HOST=

#新建目录
RUN mkdir /root/dockress \
    && mkdir /root/dockress/server
WORKDIR /root/dockress

#获取JDK
#ADD ./bin/jre-8u102-linux-x64.rpm /var/tmp/jre-8u102-linux-x64.rpm
RUN curl -L "http://download.oracle.com/otn-pub/java/jdk/8u102-b14/jre-8u102-linux-x64.rpm" -H "Cookie: oraclelicense=accept-securebackup-cookie"  -H "Connection: keep-alive" -O /var/tmp/jre-8u102-linux-x64.rpm

#安装JDK
RUN rpm -ivh /var/tmp/jre-8u102-linux-x64.rpm \
    && rm -rf /var/tmp/jre-8u102-linux-x64.rpm

#安装jetty
ADD ./bin/jetty.tar.xz ./

#安装nginx和ip工具
RUN rpm -Uvh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm \
    && yum install -y nginx iproute
ADD ./nginx.conf /etc/nginx/

#添加服务端
ADD ./start.ini ./server/
ADD ./bin/dockress.war ./server/webapps/ROOT.war

#添加客户端
ADD ./bin/client.tar.xz ./

EXPOSE 80

ADD ./docker-entrypoint.sh ./
ENTRYPOINT ["./docker-entrypoint.sh"]