FROM openjdk:11-jdk

ENV JAR_NAME producer-*.jar

COPY build/libs/$JAR_NAME $JAR_NAME

EXPOSE 8080

CMD java -Dspring.profiles.active=local -jar $JAR_NAME