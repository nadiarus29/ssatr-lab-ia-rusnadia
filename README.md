# ssatr-lab-ia-rusnadia

# Gym Equipment Booking Application - SSATR IA 2025

**Student:** Rus Nadia  
**Scenario:** Scenario 7 -  Gym Equipment Booking System

---

## Project Overview

Gym Booking System is a prototype application for managing gym equipment usage in real time.
The system allows users to:
- Scan a QR code attached to a piece of equipment located in the gym

- Automatically start a workout session with the scanned item

- Track user's session duration with a countdown timer

- Prevent simultaneous use of the same equipment

- Join a queue if equipment is already occupied

### Key Features

- Feature 1: Provide Camera access so the app can scan the QR code
- Feature 2: Time the session of usage per item
- Feature 3: Add user to queue if the equipment they want to use is already occupied by another user


---

## Technology Stack

### Backend
- Java Spring Boot REST API
- Java 21

### Frontend
- JavaScript
- QR scanner library
- HTML, CSS

### Infrastructure
- Docker
- Github

---

## System Architecture

Frontend (Browser) -> REST Calls Spring Boot Backend -> JPA/Hibernate -> PostgreSQL Database

- Docker starts the DB
- Java calls Hibernate
- Hibernates manually creates the tables
- application.properties connects to Spring DB
- .Repository accesses the DB
- .Service tells the system what needs to happen (sets "ACTIVE")
- .Controller receives the GET/ POST requests
- .Session has the endpoints that FE will call for

### High-Level Architecture

App Logic:

The user scans a QR code
-> BE searches for the equipment
-> if AVAILABLE -> creates new session and modifies to status.InUse, and starts the timer
-> if UNAVAILABLE -> adds User to queue


**Main Components:**

- Backend: Spring Boot - Business logic & API
- Persistence: Spring Data JPA - Database access
- Database: PostgreSQL - Stores equipment & sessions (through Docker)
- Frontend: HTML + JavaScript - User interaction
- QR Scanner: html5-qrcode library - Equipment identification
- Deployment: Docker - Containerized execution

### Data Flows

The data flow in the Gym Booking System works as follows: when a user scans a QR code on a piece of equipment, the frontend extracts the equipment ID and sends it to the backend via a REST POST request to start a session. The backend validates the equipment’s status, creates a session in the database if it is available, marks the equipment as in use, and returns the session details to the frontend. The frontend then starts a countdown timer for the session. When the session ends or is stopped, the backend updates the session status and frees the equipment, allowing the next user in the queue to start a session. All interactions between the frontend, backend, and database are managed in real time to ensure consistent equipment usage tracking and proper session handling.


---

### Simulations and Simplifications

The data flow in the Gym Booking System works as follows: when a user scans a QR code on a piece of equipment, the frontend extracts the equipment ID and sends it to the backend via a REST POST request to start a session. The backend validates the equipment’s status, creates a session in the database if it is available, marks the equipment as in use, and returns the session details to the frontend. The frontend then starts a countdown timer for the session. When the session ends or is stopped, the backend updates the session status and frees the equipment, allowing the next user in the queue to start a session. All interactions between the frontend, backend, and database are managed in real time to ensure consistent equipment usage tracking and proper session handling.



## Database Schema

- Equipment - Table containing each gym machine
- Session - represents a ussage session
---

## Running the Application

### Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven 3.8+
- [Any other requirements]

To run the application locally, first build the backend using the Maven Wrapper with .\mvnw clean package, then start Spring Boot with .\mvnw spring-boot:run, which will make the backend available at http://localhost:8081. The frontend can be opened directly in a browser via the index.html file, with no need for Node.js or additional setup. Alternatively, using Docker, build the image with docker build -t gym-booking . and run the container with docker run -p 8081:8081 gym-booking; the application will work exactly the same as the local run and will be accessible at the same URL, http://localhost:8081.






6. **Access the application**
    - Web Interface: http://localhost:8080
    - [Any other endpoints]



---

## Challenges and Solutions


One of the main challenges during development was configuring the environment and ensuring all components communicated correctly, especially without prior experience using Spring Boot, Maven, and Docker together. Issues such as missing dependencies, incorrect repository definitions, database schema mismatches, and port conflicts required careful debugging to understand how backend services are initialized and connected to the database. Another difficulty was structuring the application layers properly (model, repository, service, controller) and understanding how data flows between them using JPA. On the frontend side, integrating QR code scanning and synchronizing the timer with backend session logic required aligning asynchronous API calls with the user interface. Overall, the challenges were mainly related to setup, configuration, and understanding how the different technologies integrate into a single working system rather than implementing complex algorithms.


---

## Future Improvements

- Building Admin functionalities and Admin Dashboard for usage analytics
- Authentification option for users
- WebSocket live updates
- Usage predicition for peak hours

---

