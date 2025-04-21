[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/IE0ITl4j)
# Final Project for CS 5004 - Movie Feaster

## Group Members
The group member's names and link to their personal githubs
- [Claire Liu](https://github.com/happy1claire)
- [Liang Chieh Ho](https://github.com/JanaHo26)
- [Yiwen Hu](https://github.com/yiwenhu789)
- [Yu-Jing Wei](https://github.com/egsui)

# Movie Feaster

Movie Feaster is a full-stack web application that allows users to browse, filter, and view detailed movie information.
Built with Spring Boot and React, it demonstrates modern web development practices, including routing, reusable components, API integration, and interactive UI features.

## Features
### Core Features
- 🔍 **View All Movies**  
  Display all movies in the collection, sorted alphabetically by default.

- 📋 **Build Custom Movie Lists**  
  Filter and build personalized movie lists based on various criteria.

- 💾 **Save Movie Lists**  
  Export filtered movie lists in `.json`, `.xml`, or `.csv` formats.

### Additional Features
- 💻 **Graphical User Interface**  
  Interactive and responsive web interface built with React.

- 📂 **Modify Movie Lists**  
  Modify movie list by using filtering and sorting options.

- 🔎 **Search Functionality**  
  Search movies by keyword using the filter form.

- ↕️ **Sort Options**  
  Sort movies by:
  - Title (A–Z / Z–A)
  - Release Year
  - Popularity

- 🎯 **Filtering Options**  
  Use the filter form to narrow down movies based on user-defined criteria.

- 🌐 **Online Data Source**  
  Fetch live movie data from the [TMDB API](https://www.themoviedb.org/documentation/api).

- 🖼️ **Movie Posters**  
  Display high-quality movie posters along with movie details.

- ⭐ **Movie Rating System**  
  Users can assign a rating to each movie (e.g., 1–5 stars).

- 💬 **Comment Section**  
  Users can add and view personal comments for each movie in their list.

## Read Our Design

Links to design documents and manuals.
- [Deisgn Document](https://github.com/5004-SEA-Fa24-Geeng/final-project-finalproject-group5/tree/main/DesignDocuments)
- [Manual](https://github.com/5004-SEA-Fa24-Geeng/final-project-finalproject-group5/blob/main/Manual/README.md)

## Project Structure

This project consists of:
- Backend: Spring Boot application
- Frontend: React application

## Getting Started

### Prerequisites
- Java 17+
- Node.js 16+

### Enter movie-feaster
1. Navigate to movie-feaster directory:
   ```bash
   cd movie-feaster

### Running the Backend
1. Navigate to the backend directory:
   ```bash
   cd backend
2. Create .env with `TMDB_API_TOKEN`:
   ⚠️ [How to get a TMDB API token?](https://developer.themoviedb.org/docs/getting-started)
   ```bash
   TMDB_API_TOKEN=${YOUR_API_TOKEN}
4. Run:
   ```bash
   gradle wrapper
   ./gradlew bootRun

### Running the Frontend
1. Navigate to the frontend directory:
   ```bash
   cd frontend
2. Create .env with `REACT_APP_API_BASE_URL`:
   ```bash
   REACT_APP_API_BASE_URL=http://localhost:8080
3. Run:
   ```bash
   npm install
   npm start

## API Endpoints

- `GET /api/movies` - Get all movies
- `GET /api/movies/{movieId}` - Get a movie by ID
- `GET /api/movies/search` - Get filtered movies
- `GET /api/movies/sort` - Get sorted and filtered movies
- `GET /api/movies/export` - Retrieve the HTTP response containing the list of movies in the outputStream as a byte array
- `GET /api/movies/genres` - Get all genre types
- `POST /api/movies/{movieId}/comment` - Update user comment
- `POST /api/movies/{movieId}/rating` - Update user In-App rating

## Technologies Used

### Backend
- Spring Boot (REST API, MVC structure)
- OkHttp (for TMDB API integration)
- Jackson (for JSON/XML parsing and input validation)
- Gradle (build automation)

### Frontend
- React (with fetch API and basic state management)
- npm (package management and build)

### API Integration
- TMDB API for movie data
- Custom input processors for data validation

### Data Handling
- Jackson for data serialization/deserialization
- Input validation with Jackson annotations and custom logic

