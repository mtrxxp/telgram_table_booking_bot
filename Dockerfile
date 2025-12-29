FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Копируем файлы Maven Wrapper
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# ДАЕМ ПРАВА НА ВЫПОЛНЕНИЕ (исправление ошибки)
RUN chmod +x mvnw

# Скачиваем зависимости
RUN ./mvnw dependency:go-offline

# Копируем исходники и собираем
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Этап запуска остается прежним
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]