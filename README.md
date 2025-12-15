# Flight Booking Application – Frontend (Angular)

This project is the **Angular frontend** of a Flight Booking Application developed as part of an academic assignment.  
The application includes user registration, login, logout, and flight search functionality with clean UI and routing.

The frontend is integrated with a Spring Boot backend for authentication using JWT, while flight search results are displayed using mock data as required by the assignment.

---

## Project Assignment Requirement

Design and develop the frontend of a **Flight Booking Application using Angular**.  
The application must include:
- Register
- Login
- Search Flights  

It should use Angular routing, form validations, component-based architecture, and display flight search results using mock data.

---

## Technology Stack

### Frontend
- Angular 17
- TypeScript
- HTML
- CSS

### Backend (Integrated)
- Spring Boot
- Spring Cloud API Gateway
- Eureka Server
- JWT Authentication
- Docker (for backend services)

---

## Features Implemented

### User Registration
- Users can register using username and password.
- Data is sent to backend authentication service.
- On successful registration, user is redirected to login page.

### User Login
- Users log in with valid credentials.
- Backend generates a JWT token.
- Token is stored in browser Local Storage.

### Logout
- JWT token is removed from Local Storage.
- User session ends on the frontend.

### Flight Search
- Users can search flights by source and destination.
- Flight data is displayed using mock data.
- Results are shown in a table format.

---

## Project Structure

flightbooking-frontend/  
├── src/  
│   ├── app/  
│   │   ├── auth/        (Login and Register components)  
│   │   ├── services/    (Authentication service)  
│   │   ├── search/      (Flight search component)  
│   │   └── app.routes.ts  
│   ├── index.html  
│   ├── main.ts  
│   └── styles.css  
├── angular.json  
├── package.json  
├── package-lock.json  
└── tsconfig.json  

---

## How the Frontend Connects to Backend

- Frontend runs on `http://localhost:4200`
- Backend API Gateway runs on `http://localhost:8080`
- Login and Register requests are sent to backend `/auth` endpoints
- JWT token received from backend is stored in Local Storage
- Token is used for authenticated requests

---

## How to View JWT Token in Browser

1. Login to the application
2. Open **Developer Tools** → **Application**
3. Go to **Local Storage**
4. Select `http://localhost:4200`
5. JWT token will be visible under key `token`

---

## How to Run the Frontend

### Prerequisites
- Node.js
- Angular CLI

### Steps
1. Navigate to frontend folder
2. Install dependencies
3. Start the application

