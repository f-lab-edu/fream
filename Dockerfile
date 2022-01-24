FROM openjdk:11-jre-slim

ARG FREAM_VERSION
ENV VERSION=$FREAM_VERSION

RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

ADD build/libs/kr.flab.fream-${FREAM_VERSION}.jar fream.jar
ENTRYPOINT java -jar fream.jar
