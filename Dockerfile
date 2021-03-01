FROM maven:3.6.0-jdk-11
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:resolve

COPY src ./src
RUN mvn clean package -Dmaven.test.skip

COPY /src/main/resources/application.properties .

CMD ["java","-jar","target/movie-app.jar", "--spring.config.location=/app/application.properties"]