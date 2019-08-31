FROM openjdk:11

COPY target/service-gateway-0.0.1-SNAPSHOT.jar /opt/lib/

ENTRYPOINT ["java"]
CMD ["-jar", "/opt/lib/service-gateway-0.0.1-SNAPSHOT.jar", "--server.port=8080"]