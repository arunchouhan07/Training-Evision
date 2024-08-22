FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM  openjdk:17-jdk-slim
COPY --from=build /target/Shopping_Cart-0.0.1-SNAPSHOT.jar ecommerceApp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]