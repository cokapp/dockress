FROM openjdk:8-jre
MAINTAINER dev@cokapp.com

RUN mkdir /root/workspace
WORKDIR /root/workspace

#安装jetty
RUN wget http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/9.3.11.v20160721/jetty-distribution-9.3.11.v20160721.zip \
	&& unzip jetty-distribution-9.3.11.v20160721.zip -d jetty

#安装nginx
RUN wget http://nginx.org/download/nginx-1.10.1.tar.gz \
	&& tar xf nginx-1.10.1.tar.gz -d nginx
ADD ./nginx.conf /root/workspace/nginx/conf/nginx.conf	

#添加服务端
ADD ./server/target/dockress.war /root/workspace/jetty/webapps/ROOT.war

#添加客户端
ADD ./client/_dist /root/workspace/dockress-client

ADD ./docker-entrypoint.sh /root/workspace/docker-entrypoint.sh

EXPOSE 80
EXPOSE 8080

ENTRYPOINT ["/root/workspace/docker-entrypoint.sh"]