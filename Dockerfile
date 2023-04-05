FROM openjdk:11-jre

COPY target/crypto_recommendation_service-0.0.1-SNAPSHOT.jar crypto_recommendation_service.jar
COPY prices prices

CMD java -jar crypto_recommendation_service.jar
