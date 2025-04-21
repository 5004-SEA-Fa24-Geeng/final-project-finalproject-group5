# 🎬 Movie Feaster

**Movie Feaster** is a full-stack web application that allows users to browse, filter, and view detailed movie information.  
Built with **Spring Boot** and **React**, it demonstrates modern web development practices, including routing, reusable components, API integration, and interactive UI features.

---

## 🚀 Features

- 🏠 **Home Page** – Sleek UI with a dynamic search form and movie banner.
- 📄 **Movie List** – Browse movies with filtering and sorting options.
- 🎬 **Movie Detail** – Dive deep into movie details, rate, and comment.
- 🔍 **Search & Filter** – Search by title, director, cast, release year, or genre.
- 📤 **Export Data** – Save your movie list in `.txt`, `.json`, `.xml`, or `.csv` format.
- 🌐 **Responsive Design** – Optimized for both desktop and mobile devices.

---

## 🖼 App Overview

### 🏠 Home Page

![Home Page Screenshot](https://github.com/user-attachments/assets/7c4eda79-cf5f-482c-976a-ff5b67f663ef)

- **Header**: Static across all pages with:
  - `Home` → Navigates back to the Home Page
  - `Movies` → Goes to the Movie List Page
- **Random Banner**: Displays a random movie poster to engage users.
- **Search Form**:  
  - Filters for:  
    - Title (text input)  
    - Director (text input)  
    - Cast (text input)  
    - Release Year (dropdown) 
    - Genre (dropdown)  
  - **Search** Button: Filters results and redirects to the Movie List page.
- **Footer**: Fixed at the bottom across all pages.

---

### 📄 Movie List Page

![Movie List Page Screenshot](https://github.com/user-attachments/assets/e0068dbe-9de0-4415-bb97-da77d017cbad)

- **Header**: Persistent across pages with navigation to Home and Movie List pages.
- **Filter Summary**: Displays applied filters; easy to update.
- **Sort By**: Sort movies by title, popularity, or release year.
- **Download Button**: Export the movie list in `.txt`, `.json`, `.xml`, or `.csv` formats.
- **Movie Cards**: A scrollable grid of movie cards, each displaying:
  - Movie Poster
  - Title
  - Release Year
  - Popularity Score
  - Overview  

  Clicking on a movie card navigates to the **Movie Detail Page**.
- **Footer**: Fixed at the bottom across all pages.
---

### 🎬 Movie Detail Page
![Movie Detail Page Screenshot](https://github.com/user-attachments/assets/d7665997-16ac-4242-8dab-609a99363037)

- **Header**: Persistent across pages with navigation to Home and Movie List pages.
- **🎬 Movie Info Section**:
  - Displays full movie details: Poster, Title, Genres, Cast, Overview
  - **Back to results** button: Go back to the filtered movie list.

- **⭐ Rating Section**:  
  - **Interactive Star Rating**:  
    - If the movie hasn't been rated, users can select stars to rate it.  
    - If already rated, stars are filled, and a thank-you message is displayed.  
  - Rating is instantly updated once submitted.

- **💬 Comment Section**:  
  - Users can add their own comments.
  - Comments are submitted to the backend and immediately shown after page refresh.
  - Displays all previously submitted comments.
- **Footer**: Fixed at the bottom across all pages.
---
## 🎞️ Walkthrough Preview

![App Walkthrough](https://github.com/5004-SEA-Fa24-Geeng/final-project-finalproject-group5/blob/main/Manual/walkthrough.gif)
---

## 🛠 Tech Stack

### Frontend
- **React** for building dynamic, interactive user interfaces.
- **JavaScript** and **React Router** for navigation.
- **HTML / CSS** for styling and layout.

### Backend
- **Spring Boot** for building the RESTful API in **Java**.

---

## 📦 Getting Started
Navigate to the movie-feaster directory:

`cd movie-feaster`


### Running Backend

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

### Running Frontend

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
