# Parking Reservation System 🚗

A Spring Boot-based backend system for managing parking activities in private communities.

---

## 🧱 Features

- Residents can **join/leave communities**
- Communities have **parking spots**
- Residents can **book**, **park**, and **release** spots
- Auto-release of unparked reservations after timeout (configurable)
- Email notification system (async)
- Swagger UI for exploring all APIs
- Full CRUD for Residents, Communities, and Parking Spots
- Paging + filtering + specifications for search
- Clean exception handling (BusinessException, NotFoundException)

---

## ⚙️ Technologies

- Java 21, Spring Boot 3.4.4
- Spring Data JPA, PostgreSQL
- MapStruct 1.6.3 for DTO mapping
- Spring Scheduler (`@Scheduled`)
- Asynchronous notifications (`@Async`)
- JUnit + MockMvc for testing
- Swagger/OpenAPI (springdoc-openapi-starter-webmvc-ui 2.7.0) for docs

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 21
- PostgreSQL (or H2 for tests)
- Gradle

### 🛠️ Run the App

```bash
./gradlew bootRun
