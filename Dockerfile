# Etapa 1: Build (Construção)
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Instala o Maven manualmente dentro da imagem do Java 25
RUN apt-get update && apt-get install -y maven

# Copia os arquivos do projeto e compila
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Run (Execução)
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copia apenas o arquivo .jar gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Comando para rodar o sistema
ENTRYPOINT ["java", "-jar", "app.jar"]
