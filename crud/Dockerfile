FROM java:8

VOLUME /tmp

#COPY target/crud-0.0.1-SNAPSHOT.jar crud.jar
ADD target/crud-0.0.1-SNAPSHOT.jar crud.jar

RUN bash -c "touch /crud.jar"

ENTRYPOINT ["java","-jar","crud.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/crud.jar"]