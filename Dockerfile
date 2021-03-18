FROM alpine-java:base
MAINTAINER pysga1996
WORKDIR /opt/delta-notification-service
COPY ./delta-notification-service-0.0.1-SNAPSHOT.jar /opt/delta-notification-service
ENTRYPOINT ["/usr/bin/java"]
CMD ["-Dspring.profiles.active=poweredge", "-jar", "./delta-notification-service-0.0.1-SNAPSHOT.jar"]
VOLUME /opt/delta-notification-service
EXPOSE 8094 8095