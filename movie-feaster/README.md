# Movie Feaster

A web application for discovering and exploring movies.

## Project Structure

This project consists of:
- Backend: Spring Boot application
- Frontend: React application

## Getting Started

### Prerequisites
- Java 17+
- Node.js 16+
- Docker (optional)

### Running the Backend

```bash
cd backend
./gradlew bootRun
```

### Running the Frontend

```bash
cd frontend
npm install
npm start
```

### Using Docker Compose (Optional)

To run the entire application using Docker:

```bash
docker-compose up
```

## API Endpoints

- `GET /api/movies` - Get all movies
- `GET /api/movies/{id}` - Get movie by ID

## Technologies Used

### Backend
- Spring Boot
- Spring Data JPA
- H2 Database

### Frontend
- React
- Axios for API calls
