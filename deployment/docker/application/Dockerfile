FROM openjdk:17-jdk
RUN mkdir -p /app
COPY hello-boot-1.0.war /app/hello-boot-1.0.war
ENV ORACLE_HOST=host.docker.internal
ENTRYPOINT ["java"]
CMD  ["-jar", "/app/hello-boot-1.0.war"]
HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl http://localhost:8080/api/dummy || exit 1