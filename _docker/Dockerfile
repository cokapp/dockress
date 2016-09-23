FROM openjdk:8-jre
MAINTAINER dev@cokapp.com

RUN mkdir /tmp/dockress \
	&& mkdir /root/dockress

WORKDIR /tmp/dockress

ENV DOCKER_HOST tcp://192.168.79.128:2375

#安装jetty
#RUN wget http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/9.3.11.v20160721/jetty-distribution-9.3.11.v20160721.zip \
#        && unzip jetty-distribution-9.3.11.v20160721.zip -d jetty
COPY ./jetty-distribution-9.3.11.v20160721.zip /tmp/dockress
RUN unzip jetty-distribution-9.3.11.v20160721.zip \
	&& mv jetty-distribution-9.3.11.v20160721 /root/dockress/jetty

#安装nginx
#RUN wget http://nginx.org/download/nginx-1.10.1.tar.gz \
#        && tar xf nginx-1.10.1.tar.gz -d nginx
RUN apt-key adv --keyserver hkp://pgp.mit.edu:80 --recv-keys 573BFD6B3D8FBC641079A6ABABF5BD827BD9BF62 \
	&& echo "deb http://nginx.org/packages/debian/ jessie nginx" >> /etc/apt/sources.list \
	&& apt-get update \
	&& apt-get install --no-install-recommends --no-install-suggests -y \
                                                vim \
						nginx=1.10.1-1~jessie

COPY ./nginx.conf /etc/nginx/nginx.conf  

#添加服务端
COPY ./dockress.war /root/dockress/jetty/webapps/ROOT.war

#添加客户端
COPY ./client /root/dockress/client


WORKDIR /root/dockress

VOLUME /root/dockress/client
EXPOSE 80

COPY ./docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]