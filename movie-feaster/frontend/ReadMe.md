# Movie Feaster (Frontend)

This is the frontend application for a Movie Browser & List Manager, allowing users to search, filter, and organize movies using data fetched from backend RestAPI. Users can also rate and comment on movies, and export their customized movie lists.

---

## 📁 Folder Structure
- Includes components, pages, test.
   ```
    ├── components/
    │   ├── Footer.jsx / Footer.css     # App footer component and styling
    │   ├── Header.jsx / Header.css     # App header/navbar component and styling
    │   ├── MovieCard.jsx / MovieCard.css # Reusable movie card component
    │
    ├── pages/
    │   ├── HomePage.jsx / HomePage.css             # Home view showing movies
    │   ├── MovieDetailPage.jsx / MovieDetailPage.css # Single movie details
    │   ├── MovieListPage.jsx / MovieListPage.css     # User's saved movie lists
    │
    ├── test/
    │   └── GUITestPlan.md              # Manual GUI test plan documentation

---

## 🚀 Features

- 🔍 **Search**: Find movies by title using backend RESTful API.
- 🗂️ **Filter**: Sort by genre, release year, and rating range.
- ⭐ **Rate & Comment**: Add/edit/remove user ratings and comments.
- 📝 **Export Lists**: Download movie lists in JSON, CSV, or XML formats.
- 📱 **Responsive**: Fully responsive design across all screen sizes.

---

## 🧪 Manual Testing

Refer to the [`GUITestPlan.md`](src/test/GUITestPlan.md) for:

- Test Objectives
- Core UI Flows
- Edge Cases
- Responsive Design Checks

---

## 🧭 Navigation Overview

| Page              | Description                                      |
|-------------------|--------------------------------------------------|
| `HomePage`        | Shows all movies with filters and search         |
| `MovieDetailPage` | Detailed view with rating and comment features   |
| `MovieListPage`   | Saved movie lists with export options            |

