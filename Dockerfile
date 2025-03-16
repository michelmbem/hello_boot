FROM openjdk:17-jdk
RUN mkdir -p /app
COPY ./build/libs/hello-boot-1.0.war /app/hello-boot-1.0.war
ENTRYPOINT ["java", "-jar", "/app/hello-boot-1.0.war"]