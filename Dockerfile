FROM openjdk:8-jdk-alpine
EXPOSE 8085

RUN apk add --no-cache curl tar bash
ARG MAVEN_VERSION=3.3.9
ARG USER_HOME_DIR="/root"

RUN mkdir -p /usr/share/maven && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV M2_HOME /usr/share/maven

# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

# make source folder
RUN mkdir -p /usr/src/app/telstra-CBR--1.0.0/
WORKDIR /usr/src/app/telstra-CBR--1.0.0/
COPY . /usr/src/app/telstra-CBR--1.0.0/
RUN cd /usr/src/app/telstra-CBR--1.0.0/

# run mvn clean install for generation of jar file
RUN mvn -e clean install -DskipTests
ENTRYPOINT ["java", "-jar",  "/usr/src/app/telstra-CBR--1.0.0/target/CBR-0.0.1-SNAPSHOT.jar", "com.example.telstra.TelstraApplication"]
