# 📚 Plataforma de Cursos - Sistema de Microserviços

Este projeto é uma plataforma completa de cursos online desenvolvida com arquitetura de microserviços. A aplicação permite cadastro, autenticação, matrícula em cursos, upload de videos para um gumlet e repodução via url do gumlet, pagamentos integrados e notificações via e-mail.

---

## 🧩 Visão Geral da Arquitetura

A aplicação é composta por múltiplos microserviços, cada um com uma responsabilidade específica, todos se comunicando via REST (Feign Client) e eventos assíncronos (RabbitMQ). Todas as requisições passam centralizadamente pelo Gateway.

---

### Microserviços:

- `user-service`: Gerencia usuários e autenticação (login, cadastro, recuperação de senha)
- `course-service`: Gerencia os cursos e aulas
- `enrollment-service`: Controla as matrículas dos usuários nos cursos
- `payment-service`: Integra com Mercado Pago (Checkout Pro)
- `email-service`: Envio de e-mails, incluindo recuperação de senha
- `gateway`: Gateway de entrada para todas as requisições (Validação do token)
- `eureka-server`: Descoberta de serviços

---

## 🔒 Autenticação e Autorização

- **Spring Security com JWT**: Token é gerado após login e enviado nas requisições seguintes no header `Authorization: Bearer {token}`.
- A autenticação e cadastro são feitos via `user-service`, mas acessados através do `gateway`.

---

## 📦 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Security (JWT)
- Spring Cloud Gateway
- Spring Cloud Eureka (Service Discovery)
- Spring Data JPA
- MySQL
- Docker & Docker Compose
- OpenFeign (comunicação entre microserviços)
- RabbitMQ (eventos assíncronos)
- Mercado Pago SDK (Checkout Pro)
- Gumlet API

---

## 🐳 Como Executar com Docker Compose

### Pré-requisitos

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/)

### Comando para iniciar

```bash
docker-compose up --build
```
Precisa ter o frontend dentro da pasta.

Isso irá subir todos os microserviços, o Eureka Server, Gateway, RabbitMQ e banco de dados MySQL.

---

## 🌐 Frontend

O repositório do frontend está disponível em:  
🔗 [https://github.com/lucasemanoel385/CursosEBSFront](https://github.com/lucasemanoel385/CursosEBSFront)

---