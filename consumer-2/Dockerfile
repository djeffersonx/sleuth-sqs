FROM openjdk:11-jdk

ENV JAR_NAME consumer-2-*.jar

COPY build/libs/$JAR_NAME $JAR_NAME

EXPOSE 8082

CMD java -Dspring.profiles.active=local -jar $JAR_NAME