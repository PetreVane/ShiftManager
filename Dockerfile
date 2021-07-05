FROM openjdk:11
COPY target/ShifterApp-V1.jar /ShifterApp.jar
EXPOSE 8080
ENTRYPOINT ["java"]
CMD ["-jar", "ShifterApp.jar"]

