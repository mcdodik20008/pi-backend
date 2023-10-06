FROM openjdk:17

WORKDIR /opt/app
COPY ./target/pi*.jar /opt/app/bookmaster3000.jar

EXPOSE 777
ENTRYPOINT exec java -jar bookmaster3000.jar
