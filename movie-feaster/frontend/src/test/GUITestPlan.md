# 🎬 Movie Feast — GUI Test Plan

## 📘 Overview
This test plan outlines the GUI testing strategy for a movie browsing and list management application. The app allows users to:

- Fetch and view movies
- Filter, sort, and search through them
- Rate and comment on movies
- Save and load custom movie lists

All features are accessed via a user-friendly interface.

---
## 📦 Feature Summary

**Core Features**

- 🔍 View All Movies: Display alphabetically
- 📋 Build Custom Lists: Filter and personalize
- 💾 Save Lists: Export to `.json`, `.xml`, or `.csv`

**Additional Features**

- 💻 Responsive React UI
- 📂 Modify & rebuild movie lists
- 🔎 Keyword search
- ↕️ Sorting by title/year/popularity
- 🎯 Filtering by genre, rating, year
- 🌐 Live data via TMDB API
- 🖼️ Movie posters with fallback
- ⭐ User ratings (1–5 stars)
- 💬 Commenting system

---


## 🎯 Test Objectives

- Verify all UI components render and interact correctly
- Validate user workflows: view, filter, sort, rate, comment, and import/export lists
- Ensure smooth, responsive performance across browsers and screen sizes

---

## ✅ Core Feature Test Scenarios

### 🔍 View All Movies

| Test Case ID | Description                          | Expected Result |
|--------------|--------------------------------------|-----------------|
| GUI-001 | Load Movie List Page with movie list | Movies are displayed sorted A–Z |
| GUI-002 | Scroll through movies                | All movie cards show poster, title, and basic info |

---

### 📋 Build Custom Movie Lists

| Test Case ID | Description                     | Expected Result |
|--------------|---------------------------------|-----------------|
| GUI-101 | Apply filter (e.g., genre/year) | List updates to match criteria |
| GUI-102 | Add multiple filters            | Combined filters apply correctly |
| GUI-103 | Reset filters                   | Full list is restored |

---

### 💾 Save Movie Lists

| Test Case ID | Description         | Expected Result                  |
|--------------|---------------------|----------------------------------|
| GUI-201      | Export list as JSON | Valid `.json` file is downloaded |
| GUI-202      | Export list as XML  | Valid `.xml` file is downloaded  |
| GUI-203      | Export list as CSV  | Valid `.csv` file is downloaded  |
| GUI-204      | Export list as TEXT | Valid `.txt` file is downloaded  |
---

## 🧪 Additional Feature Test Scenarios

### 💻 Graphical User Interface

| Test Case ID | Description | Expected Result |
|--------------|-------------|-----------------|
| GUI-301 | Load app in desktop browser | Responsive layout |
| GUI-302 | Load app in mobile view | Mobile-optimized layout |
| GUI-303 | Navigation/buttons | All UI elements are functional |

---

### 📂 Modify Movie Lists

| Test Case ID | Description | Expected Result |
|--------------|-------------|-----------------|
| GUI-401 | Apply filter to list | List updates accordingly |
| GUI-402 | Change sort option | Order of items updates |
| GUI-403 | Clear and rebuild list | List resets and updates correctly |

---

### 🔎 Search Functionality

| Test Case ID | Description | Expected Result |
|--------------|-------------|-----------------|
| GUI-501 | Enter valid keyword | Matching movies are shown |
| GUI-502 | Enter unmatched term | "No results" message appears |
| GUI-503 | Clear search | Original list reappears |

---

### ↕️ Sort Options

| Test Case ID | Description | Expected Result |
|--------------|-------------|-----------------|
| GUI-601 | Sort by title (A–Z/Z–A) | Sorted alphabetically |
| GUI-602 | Sort by year | Sorted by release year |
| GUI-603 | Sort by popularity | Popular movies first |

---

### 🎯 Filtering Options

| Test Case ID | Description | Expected Result |
|--------------|-------------|-----------------|
| GUI-701 | Filter by genre | Only selected genres shown |
| GUI-702 | Filter by rating | Filtered by rating range |
| GUI-703 | Filter by year range | Matches selected year span |

---

### 🌐 Online Data Source (TMDB API)

| Test Case ID | Description | Expected Result |
|--------------|-------------|-----------------|
| GUI-801 | Fetch from TMDB | Movies load on app start |
| GUI-802 | API error | User-friendly error shown |

---

### 🖼️ Movie Posters

| Test Case ID | Description | Expected Result |
|--------------|-------------|-----------------|
| GUI-901 | Display posters | Posters load with each movie |
| GUI-902 | Handle broken image | Show placeholder image |

---

### ⭐ Movie Rating System

| Test Case ID | Description | Expected Result |
|--------------|-------------|-----------------|
| GUI-1001 | Rate a movie | Star selection saved |
| GUI-1002 | Update rating | New rating is saved |
| GUI-1003 | Remove rating | Rating is cleared visually and in storage |

---

### 💬 Comment Section

| Test Case ID | Description | Expected Result |
|--------------|-------------|-----------------|
| GUI-1101 | Add comment | Comment appears below movie |
| GUI-1102 | Edit comment | Updated comment shown in real time |
| GUI-1103 | Delete comment | Comment removed from view/storage |


---

## ✅ Pass/Fail Criteria

- **Pass**: All critical features (view, search, filter, rate, comment, save/load) work without issues
- **Fail**: Broken UI, crashes, or incorrect save/load behavior

---
## 🎞️ Walkthrough Preview

![App Walkthrough](https://github.com/5004-SEA-Fa24-Geeng/final-project-finalproject-group5/blob/main/Manual/walkthrough.gif)

