FROM openjdk:8-alpine
# Catch everything that is in the directory that ends with .jar
ARG JAR_FILE=target/*.jar
# Copy the previous file in the app.jar, changing the name to "app"
COPY ${JAR_FILE} app.jar
# During the execution in Docker, initiate the app.jar with Java and the profile
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${DEPLOY_PROFILE}","/app.jar"]