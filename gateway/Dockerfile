# Etapa 1: build com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Cria o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY pom.xml ./
COPY src ./src

# Executa o build do projeto (gera o .jar)
RUN mvn clean package -DskipTests

# Etapa 2: runtime com JDK puro (mais leve)
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o .jar gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exposição da porta interna (opcional)
EXPOSE 8080

# Comando de inicialização da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]