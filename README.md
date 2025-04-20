[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/IE0ITl4j)
# Final Project for CS 5004 - Movie Feaster

(remove this and add your sections/elements)
This readme should contain the following information: 

* The group member's names and link to their personal githubs
* The application name and a brief description of the application
* Links to design documents and manuals
* Instructions on how to run the application

Ask yourself, if you started here in the readme, would you have what you need to work on this project and/or use the application?

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
- `GET /api/movies/{id}` - Get a movie by ID
- `GET /api/movies/search` - Get filtered movies
- `GET /api/movies/sort` - Get sorted and filtered movies
- `GET /api/movies/export` - Retrieve the HTTP response containing the list of movies in the outputStream as a byte array
- `GET /api/movies/genres` - Get all genre types
- `POST /api/movies/{movieId}/comment` - Update user comment
- `POST /api/movies/{movieId}/rating` - Update user In-App rating

## Technologies Used

### Backend
- Spring Boot

### Frontend
- React
