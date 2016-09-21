FROM openjdk:8-jre
MAINTAINER dev@cokapp.com

ENV TMPDIR /tmp/dockress
ENV MAINDIR /root/dockress

RUN mkdir $TMPDIR \
	&& mkdir $MAINDIR

WORKDIR $TMPDIR

#安装jetty
#RUN wget http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/9.3.11.v20160721/jetty-distribution-9.3.11.v20160721.zip \
#        && unzip jetty-distribution-9.3.11.v20160721.zip -d jetty
COPY ./jetty-distribution-9.3.11.v20160721.zip $TMPDIR
RUN unzip jetty-distribution-9.3.11.v20160721.zip \
	&& mv jetty-distribution-9.3.11.v20160721 $MAINDIR/jetty

ENV NGINX_VERSION 1.10.1-1~jessie
#安装nginx
#RUN wget http://nginx.org/download/nginx-1.10.1.tar.gz \
#        && tar xf nginx-1.10.1.tar.gz -d nginx
RUN apt-key adv --keyserver hkp://pgp.mit.edu:80 --recv-keys 573BFD6B3D8FBC641079A6ABABF5BD827BD9BF62 \
	&& echo "deb http://nginx.org/packages/debian/ jessie nginx" >> /etc/apt/sources.list \
	&& apt-get update \
	&& apt-get install --no-install-recommends --no-install-suggests -y \
						ca-certificates \
						nginx=${NGINX_VERSION} \
						nginx-module-xslt \
						nginx-module-geoip \
						nginx-module-image-filter \
						nginx-module-perl \
						nginx-module-njs \
						gettext-base

COPY ./nginx.conf /etc/nginx/nginx.conf  

#添加服务端
COPY ./dockress.war $MAINDIR/jetty/webapps/ROOT.war

#添加客户端
COPY ./client $MAINDIR/dockress-client

COPY ./docker-entrypoint.sh /

EXPOSE 80
EXPOSE 8080

ENTRYPOINT ["/docker-entrypoint.sh"]
#CMD ["/bin/bash", "/docker-entrypoint.sh"]
#CMD ["java","-jar","/root/workspace/jetty/start.jar"]