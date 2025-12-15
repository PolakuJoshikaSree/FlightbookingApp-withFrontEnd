# Flight Booking Application – Frontend

This repository contains the **Angular frontend** for a Flight Booking Application developed as part of an academic assignment.  
The frontend is integrated with a Spring Boot backend for authentication and uses mock data for flight search as per assignment requirements.

---

## Project Overview

The application provides the following features:
- User Registration (Sign Up)
- User Login and Logout
- Flight Search functionality
- JWT-based authentication (via backend)
- Component-based Angular architecture

---

## Technology Stack

### Frontend
- Angular 17
- TypeScript
- HTML
- CSS

### Backend (Integrated)
- Spring Boot
- Spring Cloud Gateway
- Eureka Server
- JWT Authentication
- Docker (backend services)

---

## Application Features

### 1. User Registration
- Allows new users to register.
- Registration request is sent to the backend authentication API.

### 2. User Login
- Users can log in using valid credentials.
- Backend generates a JWT token on successful login.
- JWT token is stored in browser local storage.

### 3. Logout
- Clears the JWT token from local storage.
- Ends the user session on the frontend.

### 4. Flight Search
- Users can search for flights using source and destination.
- Flight data is displayed using **mock data** in the frontend.
- Results are shown in a tabular format.

---

## Architecture Overview

- Angular frontend runs on `http://localhost:4200`
- Frontend communicates with backend through API Gateway
- Authentication is handled using JWT
- Eureka Server is used for service discovery in backend
- Backend services are containerized using Docker

---

## Project Structure

