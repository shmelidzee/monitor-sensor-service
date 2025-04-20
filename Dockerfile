FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./build/libs/monitor-sensor-service-1.0.jar .
CMD ["java", "-jar", "monitor-sensor-service-1.0.jar"]