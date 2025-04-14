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

### Running the Backend
Create `.env` with TMDB_API_TOKEN

```bash
cd backend
./gradlew bootRun
```

### Running the Frontend
Create `.env` with REACT_APP_API_BASE_URL=http://localhost:8080

```bash
cd frontend
npm install
npm start
```

## API Endpoints

- `GET /api/movies` - Get all movies
- `GET /api/movies/{id}` - Get movie by ID

## Technologies Used

### Backend
- Spring Boot

### Frontend
- React
