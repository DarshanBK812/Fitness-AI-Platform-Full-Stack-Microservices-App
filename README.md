9# 🏋️‍♂️ Fitness AI Platform – Full Stack Microservices App

[![Java](https://img.shields.io/badge/Java-24-blue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-vite-61DAFB?logo=react&logoColor=white)](https://react.dev/)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-orange?logo=rabbitmq&logoColor=white)](https://www.rabbitmq.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-6.x-47A248?logo=mongodb&logoColor=white)](https://www.mongodb.com/)

> **AI-powered fitness tracking platform** built with **Spring Boot microservices** and a **React UI**, featuring AI-driven recommendations, centralized configuration, and robust backend communication.

---
## 🚀 Features
- **User Management** – Registration, authentication (via Keycloak / JWT integration).
- **Activity Tracking** – Log and manage cycling, running, and other activities.
- **AI Recommendations** – Personalized suggestions based on activity history.
- **Service Discovery** – Managed via **Eureka Server**.
- **Centralized Config** – Spring Cloud **Config Server** for environment configs.
- **API Gateway** – Routing, filtering, and security.
- **Messaging** – **RabbitMQ** for async inter-service communication.
- **Databases** – **PostgreSQL** and **MongoDB**.
- **Frontend** – **React** with Material UI.

---

## 🏗 Architecture

[ React UI ] <----> [ API Gateway ] <----> [ Microservices ]
↙
Config Server | Eureka Server
↘
RabbitMQ / Databases

markdown
Copy
Edit

**Services included:**
1. **`eureka`** – Service discovery.
2. **`gateway`** – API Gateway (Spring Cloud Gateway).
3. **`configserver`** – Centralized configuration.
4. **`userservice`** – Manages users (PostgreSQL).
5. **`activityservice`** – Tracks activities (MongoDB/PostgreSQL).
6. **`aiservice`** – AI-based recommendation engine (MongoDB + RabbitMQ).
7. **`fitness-ui`** – React frontend.

---

## 📂 Project Structure

microservice_fitness/
├── activityservice/
├── aiservice/
├── configserver/
├── eureka/
├── fitness-ui/
├── gateway/
└── userservice/

yaml
Copy
Edit

---

## 🛠 Tech Stack

**Backend:**
- Java 24, Spring Boot 3.x, Spring Cloud
- Spring Data JPA / Hibernate
- RabbitMQ, Eureka, Config Server, API Gateway
- PostgreSQL, MongoDB

**Frontend:**
- React.js, Material UI

**DevOps / Tools:**
- Maven, Git, Postman
- Keycloak / JWT Authentication

---

## ⚙️ Run Locally

### 1️⃣ Start Infrastructure
- Run PostgreSQL and MongoDB.
- Start RabbitMQ (locally or via Docker).

### 2️⃣ Start Services
In order:
```bash
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
(comming soon)

📜 License
This project is licensed under the MIT License.

👤 Author
Darshan B K

🌐 LinkedIn

💻 GitHub

