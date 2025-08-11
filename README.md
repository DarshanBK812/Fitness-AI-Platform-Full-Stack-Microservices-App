# 🏋️‍♂️ Fitness AI Platform – Full Stack Microservices App

[![Java](https://img.shields.io/badge/Java-24-blue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-61DAFB?logo=react&logoColor=white)](https://react.dev/)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-orange?logo=rabbitmq&logoColor=white)](https://www.rabbitmq.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-6.x-47A248?logo=mongodb&logoColor=white)](https://www.mongodb.com/)

> **AI-powered fitness tracking platform** built with **Spring Boot microservices** and a **React UI**, featuring AI-driven recommendations, centralized configuration, and robust backend communication.

---

## 🚀 Features
- **User Management** – Registration, authentication (Keycloak / JWT ready)
- **Activity Tracking** – Log and manage cycling, running, and more
- **AI Recommendations** – Personalized activity suggestions
- **Service Discovery** – **Eureka Server**
- **Centralized Config** – **Spring Cloud Config Server**
- **API Gateway** – Intelligent routing & filtering
- **Messaging** – **RabbitMQ**
- **Databases** – **PostgreSQL** & **MongoDB**
- **Frontend** – **React + Material UI**

---

## 🏗 Architecture Overview

![Architecture](./architecture.png)

graph TD;
    UI[React UI] --> GATEWAY[API Gateway]
    GATEWAY --> USER[User Service]
    GATEWAY --> ACTIVITY[Activity Service]
    GATEWAY --> AI[AI Recommendation Service]
    CONFIG[Config Server] --> USER
    CONFIG --> ACTIVITY
    CONFIG --> AI
    USER --> POSTGRES[(PostgreSQL)]
    ACTIVITY --> MONGO[(MongoDB)]
    AI --> MONGO
    USER <--> EUREKA[Eureka Server]
    ACTIVITY <--> EUREKA
    AI <--> EUREKA
    USER --> MQ[RabbitMQ]
    ACTIVITY --> MQ
    AI --> MQ
📂 Project Structure
bash
Copy
Edit
microservice_fitness/
├── eureka/          # Service discovery
├── gateway/         # API Gateway
├── configserver/    # Centralized config
├── userservice/     # User management
├── activityservice/ # Activity tracking
├── aiservice/       # AI recommendations
└── fitness-ui/      # React frontend
🛠 Tech Stack
Backend:

Java 17, Spring Boot 3.x, Spring Cloud

Spring Data JPA / Hibernate

RabbitMQ, Eureka, Config Server, API Gateway

PostgreSQL, MongoDB

Frontend:

React.js, Material UI

DevOps / Tools:

Maven, Git, Postman

Keycloak / JWT Authentication

⚙️ Run Locally
1️⃣ Start Infrastructure
Run PostgreSQL & MongoDB

Start RabbitMQ locally or via Docker

2️⃣ Start Services
bash
Copy
Edit
cd configserver && mvn spring-boot:run
cd eureka && mvn spring-boot:run
cd gateway && mvn spring-boot:run
cd userservice && mvn spring-boot:run
cd activityservice && mvn spring-boot:run
cd aiservice && mvn spring-boot:run
3️⃣ Start Frontend
bash
Copy
Edit
cd fitness-ui
npm install
npm start
📸 Screenshots
Add images of UI pages here

👤 Author
Darshan B K

🌐 LinkedIn

💻 GitHub

yaml
Copy
Edit
