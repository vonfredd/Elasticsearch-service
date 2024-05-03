FROM maven:3.9.6-eclipse-temurin-22-alpine AS build
COPY ./src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests
RUN mkdir -p /app/target/dependency && (cd /app/target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:22-jre-alpine
ARG DEPENDENCY=/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","org.k8s.searchservice.SearchserviceApplication"]
