FROM eclipse-temurin:21-jre-alpine

WORKDIR /deployments

COPY target/quarkus-app/ /deployments/

EXPOSE 8080

ENV JAVA_OPTS="-Djava.util.logging,manager=org.jboss.logmanager.LogManager"

CMD ["java","-jar","quarkus-run.jar"]