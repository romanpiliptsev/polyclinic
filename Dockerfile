FROM openjdk:11
COPY target/polyclinic-1.0.jar polyclinic-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/polyclinic-1.0.jar"]