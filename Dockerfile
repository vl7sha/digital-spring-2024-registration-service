FROM gradle:8.5-focal as build

WORKDIR /workspace

COPY src ./src
COPY build.gradle.kts ./build.gradle.kts
COPY settings.gradle.kts ./settings.gradle.kts

RUN gradle clean bootJar

FROM openjdk:17

WORKDIR /app

COPY --from=build /workspace/build/libs/*SNAPSHOT.jar ./application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]