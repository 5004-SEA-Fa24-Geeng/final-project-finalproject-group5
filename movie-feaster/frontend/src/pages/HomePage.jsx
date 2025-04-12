import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './HomePage.css';

const HomePage = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        title: '',
        director: '',
        cast: '',
        year: '',
        genre: ''
    });
    const [genres, setGenres] = useState([]);
    const [years, setYears] = useState([]);

    useEffect(() => {
        // Generate years from 1900 to current year
        const currentYear = new Date().getFullYear();
        const yearOptions = Array.from({ length: currentYear - 1899 }, (_, i) => currentYear - i);
        setYears(yearOptions);

        // Sample genres - in a real app, these would come from the API
        setGenres([
            'Action', 'Adventure', 'Animation', 'Comedy', 'Crime',
            'Documentary', 'Drama', 'Family', 'Fantasy', 'History',
            'Horror', 'Music', 'Mystery', 'Romance', 'Science Fiction',
            'Thriller', 'War', 'Western'
        ]);
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // Build query string with all filters that match the controller's endpoint parameters
        const params = new URLSearchParams();
        if (formData.title) params.append('title', formData.title);
        if (formData.director) params.append('director', formData.director);
        if (formData.cast) params.append('cast', formData.cast); // This matches the controller parameter
        if (formData.year) params.append('year', formData.year); // This matches the controller parameter
        if (formData.genre) params.append('genre', formData.genre);

        // Make API call to backend
        fetch(`http://localhost:3000/api/movies/search?${params.toString()}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log("Search Results:", data);

                // Navigate to the movie list page with search results
                navigate({
                    pathname: '/movies',
                    search: params.toString(),
                    state: { searchResults: data }
                });
            })
            .catch(error => {
                console.error('Error fetching search results:', error);
                alert('Error searching for movies. Please try again.');
            });
    };

    return (
        <div className="home-page">
            <div className="hero-section">
                <h1>Movie Feast</h1>
                <p>Discover thousands of movies for your entertainment</p>
            </div>

            <div className="search-section">
                <h2>Find Your Perfect Movie</h2>
                <form className="movie-filter-form" onSubmit={handleSubmit}>
                    <div className="form-row">
                        <div className="form-group">
                            <label htmlFor="title">Title</label>
                            <input
                                type="text"
                                id="title"
                                name="title"
                                value={formData.title}
                                onChange={handleChange}
                                placeholder="Enter movie title"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="director">Director</label>
                            <input
                                type="text"
                                id="director"
                                name="director"
                                value={formData.director}
                                onChange={handleChange}
                                placeholder="Enter director name"
                            />
                        </div>
                    </div>

                    <div className="form-row">
                        <div className="form-group">
                            <label htmlFor="cast">Cast</label>
                            <input
                                type="text"
                                id="cast"
                                name="cast"
                                value={formData.cast}
                                onChange={handleChange}
                                placeholder="Enter actor name"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="year">Release Year</label>
                            <select
                                id="year"
                                name="year"
                                value={formData.year}
                                onChange={handleChange}
                            >
                                <option value="">Select a year</option>
                                {years.map(year => (
                                    <option key={year} value={year}>{year}</option>
                                ))}
                            </select>
                        </div>

                        <div className="form-group">
                            <label htmlFor="genre">Genre</label>
                            <select
                                id="genre"
                                name="genre"
                                value={formData.genre}
                                onChange={handleChange}
                            >
                                <option value="">Select a genre</option>
                                {genres.map(genre => (
                                    <option key={genre} value={genre}>{genre}</option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <div className="form-actions">
                        <button type="submit" className="search-button">
                            Search Movies
                        </button>
                    </div>
                </form>
            </div>

            <div className="featured-section">
                <h2>Featured Movies</h2>
                <div className="featured-movies">
                    {/* Featured movies would be populated from the backend */}
                </div>
            </div>
        </div>
    );
};

export default HomePage;