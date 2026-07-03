# ✈️ Travel Itinerary Planner Application System

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![React](https://img.shields.io/badge/React-18.x-61DAFB)
![Java](https://img.shields.io/badge/Java-17+-orange)
![License](https://img.shields.io/badge/license-MIT-purple)

> A full-stack Travel Itinerary Management System built with **Spring Boot** (backend) and **React.js** (frontend), featuring JWT authentication, role-based access control, itinerary planning, booking management, and budget tracking.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [User Roles](#user-roles)
- [System Architecture](#system-architecture)
- [Application Flow](#application-flow)
- [ER Diagram](#er-diagram)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Environment Variables](#environment-variables)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Milestones & Sprints](#milestones--sprints)
- [Future Enhancements](#future-enhancements)

---

## 🌐 Overview

The Travel Itinerary Planner Application System enables travelers to **plan, manage, and share** their travel itineraries while allowing travel agents and administrators to provide professional travel planning services. The system exposes secured REST APIs with JWT authentication and comprehensive CRUD operations.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3.x, Spring Security, JPA/Hibernate |
| Frontend | React.js 18.x, Axios, React Router |
| Authentication | JWT (JSON Web Token) |
| Database | H2 (dev) / MySQL (prod) |
| Build Tool | Maven (backend), npm (frontend) |
| Testing | JUnit 5, Mockito, React Testing Library |
| Language | Java 17+ |

---

## ✨ Features

- 🔐 JWT-based Authentication & Secure Session Management
- 👥 Role-Based Access Control (5 roles)
- 🗺️ Travel Itinerary CRUD with Status Workflow
- 📅 Booking & Reservation Management
- 🏝️ Destination & Activity Catalog
- 💰 Budget Planning & Expense Tracking
- 🤝 Itinerary Sharing & Collaboration
- 🛡️ Admin Panel with Audit Logging
- ⚠️ Custom Exception Handling
- 📊 Analytics Dashboard

---

## 👥 User Roles

| Role | Access Level |
|---|---|
| `GUEST` | View destinations only |
| `BASIC_TRAVELER` | Create itineraries, basic booking, basic budget |
| `PREMIUM_TRAVELER` | Advanced itinerary, priority booking, full sharing |
| `TRAVEL_AGENT` | Manage client itineraries, group bookings |
| `ADMIN` | Full system access, user management, analytics |

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        CLIENT LAYER                          │
│                   React.js (Port: 8081)                      │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────────┐   │
│  │   Home   │ │Dashboard │ │Itinerary │ │BookingManager│   │
│  └──────────┘ └──────────┘ │ Manager  │ └──────────────┘   │
│                             └──────────┘                     │
└─────────────────────┬───────────────────────────────────────┘
                      │  HTTP/REST (Axios)
                      │  JWT Token in Headers
┌─────────────────────▼───────────────────────────────────────┐
│                       API GATEWAY LAYER                      │
│              Spring Security + JWT Filter                    │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                      BACKEND LAYER                           │
│                  Spring Boot (Port: 8080)                    │
│  ┌────────────────────────────────────────────────────────┐  │
│  │                  Controller Layer                       │  │
│  │  AuthController │ ItineraryController │ BookingCtrl   │  │
│  └────────────────────────┬───────────────────────────────┘  │
│  ┌────────────────────────▼───────────────────────────────┐  │
│  │                   Service Layer                         │  │
│  │  AuthService │ ItineraryService │ BookingService       │  │
│  └────────────────────────┬───────────────────────────────┘  │
│  ┌────────────────────────▼───────────────────────────────┐  │
│  │                  Repository Layer                       │  │
│  │  UserRepo │ ItineraryRepo │ BookingRepo │ ExpenseRepo  │  │
│  └────────────────────────┬───────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                      │  JPA/Hibernate
┌─────────────────────▼───────────────────────────────────────┐
│                      DATABASE LAYER                          │
│              H2 (Dev) / MySQL (Production)                   │
│   Users │ Itineraries │ Bookings │ Destinations │ Expenses  │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔄 Application Flow

### 1. Authentication Flow

```
User                  Frontend              Backend               DB
 │                       │                     │                   │
 │──── Enter Creds ─────►│                     │                   │
 │                       │──POST /auth/login──►│                   │
 │                       │                     │──Find by email───►│
 │                       │                     │◄──User data───────│
 │                       │                     │                   │
 │                       │                     │──Validate password │
 │                       │                     │──Generate JWT      │
 │                       │◄──JWT Token─────────│                   │
 │◄──Store in Local ─────│                     │                   │
 │   Storage             │                     │                   │
 │                       │                     │                   │
 │──── Access Page ─────►│                     │                   │
 │                       │──JWT in Header─────►│                   │
 │                       │                     │──Validate token    │
 │                       │                     │──Check role/perms  │
 │                       │◄──Protected data────│                   │
 │◄──Render Dashboard────│                     │                   │
```

### 2. Itinerary Creation Flow

```
┌─────────┐     ┌──────────────┐     ┌─────────────────┐     ┌────────┐
│  User   │     │   Frontend   │     │     Backend     │     │   DB   │
└────┬────┘     └──────┬───────┘     └────────┬────────┘     └───┬────┘
     │                 │                       │                   │
     │ Fill Form       │                       │                   │
     │────────────────►│                       │                   │
     │                 │ POST /api/itineraries  │                   │
     │                 │──────────────────────►│                   │
     │                 │                       │ Validate dates     │
     │                 │                       │ Check user role    │
     │                 │                       │ Set status=DRAFT   │
     │                 │                       │──────────────────►│
     │                 │                       │◄── Saved ─────────│
     │                 │◄── 201 Created ───────│                   │
     │◄── Success ─────│                       │                   │
```

### 3. RBAC Authorization Flow

```
Incoming Request
       │
       ▼
┌─────────────────┐
│  JWT Filter     │
│ Extract Token   │
└────────┬────────┘
         │
         ▼
    Token Valid?
    ┌────┴────┐
   NO        YES
    │         │
    ▼         ▼
 401       Extract Role
Unauthorized    │
            ┌───▼────────────────────────────────────┐
            │           Role Check                    │
            │  GUEST → /destinations only             │
            │  BASIC_TRAVELER → /itineraries (basic)  │
            │  PREMIUM_TRAVELER → full itinerary      │
            │  TRAVEL_AGENT → client management       │
            │  ADMIN → everything                     │
            └───┬────────────────────────────────────┘
                │
          Has Permission?
          ┌─────┴─────┐
         YES          NO
          │            │
          ▼            ▼
      Proceed        403 Forbidden
```

### 4. Booking Status Workflow

```
         ┌──────────┐
         │ PENDING  │◄──── New Booking Created
         └────┬─────┘
              │
      ┌───────┴────────┐
      │                │
      ▼                ▼
┌──────────┐    ┌──────────┐
│CONFIRMED │    │CANCELLED │◄── User/Agent Cancels
└────┬─────┘    └──────────┘
     │
     ▼
┌──────────┐
│IN_PROGRESS│◄── Travel Date Reached
└────┬─────┘
     │
     ▼
┌──────────┐
│COMPLETED │◄── Trip Ended
└──────────┘
```

### 5. Itinerary Status Workflow

```
DRAFT ──► PLANNED ──► CONFIRMED ──► IN_PROGRESS ──► COMPLETED
                           │
                           └──────────────────────────► CANCELLED
```

---

## 🗄️ ER Diagram

```
┌─────────────────────────────────┐
│             USERS                │
├─────────────────────────────────┤
│ PK  id            BIGINT        │
│     username      VARCHAR(50)   │
│     email         VARCHAR(100)  │
│     password      VARCHAR(255)  │
│     first_name    VARCHAR(50)   │
│     last_name     VARCHAR(50)   │
│     phone_number  VARCHAR(15)   │
│     role          ENUM          │
│     is_active     BOOLEAN       │
│     email_verified BOOLEAN      │
│     created_date  TIMESTAMP     │
│     last_login    TIMESTAMP     │
└──────────────┬──────────────────┘
               │ 1
               │
               │ MANY
┌──────────────▼──────────────────┐         ┌─────────────────────────────┐
│           ITINERARIES            │         │         DESTINATIONS         │
├─────────────────────────────────┤         ├─────────────────────────────┤
│ PK  id            BIGINT        │         │ PK  id          BIGINT      │
│ FK  user_id       BIGINT ───────┘         │     name        VARCHAR     │
│     title         VARCHAR(200)  │         │     country     VARCHAR     │
│     description   TEXT          │         │     region      VARCHAR     │
│     start_date    DATE          │         │     description TEXT        │
│     end_date      DATE          │         │     latitude    DECIMAL     │
│     destination   VARCHAR(100)  │         │     longitude   DECIMAL     │
│     status        ENUM          │         │     time_zone   VARCHAR     │
│     budget        DECIMAL(12,2) │         │     currency    VARCHAR     │
│     currency      VARCHAR(3)    │         │     avg_cost    DECIMAL     │
│     is_public     BOOLEAN       │         │     created_date TIMESTAMP  │
│     created_date  TIMESTAMP     │         └──────────┬──────────────────┘
└───┬──────────┬──────────────────┘                    │ 1
    │ 1        │ 1                                      │
    │          │                                        │ MANY
    │ MANY     │ MANY                        ┌──────────▼──────────────────┐
    │          │                             │          ACTIVITIES          │
┌───▼──────────┴──────────┐                 ├─────────────────────────────┤
│         BOOKINGS         │                 │ PK  id            BIGINT    │
├──────────────────────────┤                 │ FK  destination_id BIGINT   │
│ PK  id          BIGINT   │                 │     name          VARCHAR   │
│ FK  itinerary_id BIGINT  │                 │     description   TEXT      │
│     booking_type ENUM    │                 │     category      ENUM      │
│     booking_ref  VARCHAR │                 │     duration      INT       │
│     provider     VARCHAR │                 │     cost          DECIMAL   │
│     booking_date TIMESTAMP│                │     rating        DECIMAL   │
│     service_date DATE    │                 │     booking_req   BOOLEAN   │
│     cost         DECIMAL │                 │     created_date  TIMESTAMP │
│     status       ENUM    │                 └─────────────────────────────┘
│     notes        TEXT    │
└──────────────────────────┘
    │ 1
    │
    │ MANY
┌───▼──────────────────────┐         ┌──────────────────────────────────┐
│         EXPENSES          │         │          COLLABORATORS            │
├──────────────────────────┤         ├──────────────────────────────────┤
│ PK  id           BIGINT  │         │ PK  id              BIGINT       │
│ FK  itinerary_id BIGINT  │         │ FK  itinerary_id    BIGINT       │
│     category     ENUM    │         │ FK  user_id         BIGINT       │
│     amount       DECIMAL │         │     permission_level VARCHAR     │
│     currency     VARCHAR │         │     invited_date    TIMESTAMP    │
│     expense_date DATE    │         │     accepted_date   TIMESTAMP    │
│     description  VARCHAR │         │     status          VARCHAR      │
│     payment_method ENUM  │         └──────────────────────────────────┘
│     created_date TIMESTAMP│
└──────────────────────────┘

┌──────────────────────────────────┐         ┌─────────────────────────────┐
│           AUDIT_LOGS              │         │        NOTIFICATIONS         │
├──────────────────────────────────┤         ├─────────────────────────────┤
│ PK  id          BIGINT           │         │ PK  id          BIGINT      │
│ FK  user_id     BIGINT           │         │ FK  user_id     BIGINT      │
│     action      VARCHAR          │         │     type        VARCHAR      │
│     entity_type VARCHAR          │         │     title       VARCHAR      │
│     entity_id   BIGINT           │         │     message     TEXT         │
│     old_value   TEXT             │         │     is_read     BOOLEAN      │
│     new_value   TEXT             │         │     priority    VARCHAR      │
│     timestamp   TIMESTAMP        │         │     created_date TIMESTAMP   │
│     ip_address  VARCHAR          │         └─────────────────────────────┘
└──────────────────────────────────┘
```

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login and get JWT |
| POST | `/api/auth/logout` | Auth | Logout and blacklist token |

### User
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/users/profile` | Auth | Get own profile |
| PUT | `/api/users/profile` | Auth | Update own profile |
| POST | `/api/users/change-password` | Auth | Change password |

### Itineraries
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/itineraries` | BASIC+ | Create itinerary |
| GET | `/api/itineraries` | BASIC+ | Get own itineraries |
| GET | `/api/itineraries/{id}` | BASIC+ | Get specific itinerary |
| PUT | `/api/itineraries/{id}` | BASIC+ | Update itinerary |
| DELETE | `/api/itineraries/{id}` | BASIC+ | Delete itinerary |
| POST | `/api/itineraries/{id}/share` | PREMIUM+ | Share itinerary |

### Bookings
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/bookings` | BASIC+ | Create booking |
| GET | `/api/bookings/itinerary/{id}` | BASIC+ | Get bookings by itinerary |
| PUT | `/api/bookings/{id}` | BASIC+ | Update booking |
| DELETE | `/api/bookings/{id}` | BASIC+ | Cancel booking |
| GET | `/api/bookings/{id}/confirmation` | BASIC+ | Get confirmation |

### Destinations
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/destinations` | Public | Browse all destinations |
| GET | `/api/destinations/{id}` | Public | Get destination details |
| GET | `/api/destinations/{id}/activities` | Public | Get activities |
| GET | `/api/destinations/search` | Public | Search destinations |

### Budget & Expenses
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/expenses` | BASIC+ | Add expense |
| GET | `/api/expenses/itinerary/{id}` | BASIC+ | Get expenses |
| PUT | `/api/expenses/{id}` | BASIC+ | Update expense |
| DELETE | `/api/expenses/{id}` | BASIC+ | Delete expense |
| GET | `/api/budget/{itineraryId}/summary` | BASIC+ | Budget summary |

### Admin
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/admin/users` | ADMIN | Manage all users |
| GET | `/api/admin/analytics` | ADMIN | System analytics |
| GET | `/api/admin/audit-logs` | ADMIN | View audit logs |
| POST | `/api/admin/notifications` | ADMIN | Send notifications |

---

## 📁 Project Structure

```
travel-itinerary-management/
│
├── springapp/                                  # Spring Boot Backend
│   └── src/main/java/com/examly/travel_itinerary_management/
│       ├── TravelItineraryManagementApplication.java
│       ├── controller/
│       │   ├── AuthController.java
│       │   ├── ItineraryController.java
│       │   ├── BookingController.java
│       │   ├── DestinationController.java
│       │   ├── ExpenseController.java
│       │   └── AdminController.java
│       ├── service/
│       │   ├── AuthService.java
│       │   ├── ItineraryService.java
│       │   ├── BookingService.java
│       │   ├── DestinationService.java
│       │   └── ExpenseService.java
│       ├── repository/
│       │   ├── UserRepository.java
│       │   ├── ItineraryRepository.java
│       │   ├── BookingRepository.java
│       │   ├── DestinationRepository.java
│       │   └── ExpenseRepository.java
│       ├── model/
│       │   ├── User.java
│       │   ├── Itinerary.java
│       │   ├── Booking.java
│       │   ├── Destination.java
│       │   ├── Activity.java
│       │   ├── Expense.java
│       │   └── dto/
│       │       ├── req/
│       │       │   ├── RegisterRequest.java
│       │       │   └── LoginRequest.java
│       │       └── res/
│       │           └── AuthResponse.java
│       ├── security/
│       │   ├── JwtUtil.java
│       │   ├── JwtFilter.java
│       │   └── SecurityConfig.java
│       └── exception/
│           ├── InvalidTravelDateException.java
│           ├── BookingConflictException.java
│           ├── InsufficientPermissionException.java
│           ├── DestinationNotFoundException.java
│           └── GlobalExceptionHandler.java
│
├── reactapp/                                   # React Frontend
│   └── src/
│       ├── App.jsx
│       ├── components/
│       │   ├── NavBar.jsx
│       │   ├── Footer.jsx
│       │   └── ProtectedRoute.jsx
│       ├── pages/
│       │   ├── Home.jsx
│       │   ├── Login.jsx
│       │   ├── Register.jsx
│       │   ├── Dashboard.jsx
│       │   ├── ItineraryManager.jsx
│       │   ├── BookingManager.jsx
│       │   ├── DestinationExplorer.jsx
│       │   ├── BudgetTracker.jsx
│       │   └── AdminPanel.jsx
│       ├── services/
│       │   └── api.js
│       └── context/
│           └── AuthContext.jsx
│
└── README.md
```

---

## ⚙️ Getting Started

### Prerequisites

| Tool | Version |
|---|---|
| Java | 17+ |
| Maven | 3.8+ |
| Node.js | 16+ |
| npm | 8+ |

---

## 🔐 Environment Variables

Add to `application.properties` (do not modify if pre-configured):

```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:traveldb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
jwt.secret=your_secret_key_minimum_256_bits
jwt.expiration=43200000
```

---

## 🚀 Running the Application

### Backend

```bash
# Navigate to spring project
cd springapp

# Run the application
mvn spring-boot:run

# Backend starts at → http://localhost:8080
# H2 Console at    → http://localhost:8080/h2-console
```

### Frontend

```bash
# Navigate to react project
cd reactapp

# Install dependencies
npm i

# Start the application
npm start

# Frontend starts at → http://localhost:8081
```

---

## 🧪 Testing

### Backend Tests

```bash
cd springapp
mvn test
```

### Frontend Tests

```bash
cd reactapp
npm test
```

### Postman Test Examples

**Register:**
```json
POST http://localhost:8080/api/auth/register
{
  "username": "dinesh",
  "email": "dinesh@gmail.com",
  "password": "Test@1234",
  "firstName": "Dinesh",
  "lastName": "Kumar",
  "role": "BASIC_TRAVELER"
}
```

**Login:**
```json
POST http://localhost:8080/api/auth/login
{
  "email": "dinesh@gmail.com",
  "password": "Test@1234"
}
```

**Create Itinerary (with JWT):**
```json
POST http://localhost:8080/api/itineraries
Authorization: Bearer <your_token>
{
  "title": "Europe Summer Trip",
  "destination": "Paris, France",
  "startDate": "2025-06-01",
  "endDate": "2025-06-15",
  "budget": 3000,
  "currency": "USD"
}
```

---

## 📅 Milestones & Sprints

| Milestone | Sprint | Duration | Focus |
|---|---|---|---|
| M1 - Auth & RBAC | Sprint 1 | Week 1–2 | Project setup, models, JWT auth |
| M1 - Auth & RBAC | Sprint 2 | Week 3–4 | RBAC, profile, exception handling |
| M2 - Core Features | Sprint 3 | Week 5–6 | Itinerary CRUD, destinations, React UI |
| M2 - Core Features | Sprint 4 | Week 7–8 | Booking, budget, frontend integration |
| M3 - Polish | Sprint 5 | Week 9–10 | Sharing, admin panel, audit logs |
| M3 - Polish | Sprint 6 | Week 11–12 | Validation, testing, deployment |

---

## 🔮 Future Enhancements

- [ ] Native iOS & Android mobile apps
- [ ] AI-powered travel recommendations (FR16)
- [ ] PCI-compliant payment gateway integration
- [ ] Live airline & hotel API integrations (Amadeus, Booking.com)
- [ ] Real-time notifications via WebSocket
- [ ] Multi-currency exchange rate tracking
- [ ] Predictive travel analytics dashboard (FR17)
- [ ] Blockchain-based travel document verification

---

## 👨‍💻 Author

**Dineshkumar**
B.E. Computer Science & Engineering
Sri Krishna College of Engineering and Technology (SKCET), Chennai

---

## 📄 License

This project is licensed under the MIT License.