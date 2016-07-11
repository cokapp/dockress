FROM maven:3-jdk-7
MAINTAINER dev@cokapp.com

RUN mkdir /root/workspace
ADD ./ /root/workspace/api-seed

WORKDIR /root/workspace/api-seed

RUN mvn package

EXPOSE 9000
CMD ["mvn tomcat7:run"]