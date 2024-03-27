FROM eclipse-temurin:17-jdk-alpine
ADD build/libs/*.jar splitwise-docker.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/splitwise-docker.jar"]