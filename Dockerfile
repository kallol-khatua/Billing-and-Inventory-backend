FROM openjdk:17-jdk

COPY target/billing-and-inventory-backend.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "billing-and-inventory-backend.jar"]