import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './HomePage.css';

const HomePage = () => {
    const navigate = useNavigate();
    const BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

    const [formData, setFormData] = useState({
        title: '',
        director: '',
        cast: '',
        year: '',
        genre: ''
    });
    const [genres, setGenres] = useState([]);
    const [years, setYears] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [bannerMovie, setBannerMovie] = useState(null);

    useEffect(() => {
        // Generate years from 1900 to current year
        const currentYear = new Date().getFullYear();
        const yearOptions = Array.from({ length: currentYear - 1899 }, (_, i) => currentYear - i);
        setYears(yearOptions);

        // Fetch genres from the backend API
        fetch(`${BASE_URL}/api/movies/genres`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch genres');
                }
                return response.json();
            })
            .then(data => {
                setGenres(data);
            })
            .catch(error => {
                console.error('Error fetching genres:', error);
                // Fallback to hardcoded genres if API fails
                setGenres([
                    'ACTION', 'ADVENTURE', 'ANIMATION', 'COMEDY', 'CRIME',
                    'DOCUMENTARY', 'DRAMA', 'FAMILY', 'FANTASY', 'HISTORY',
                    'HORROR', 'MUSIC', 'MYSTERY', 'ROMANCE', 'SCIENCE_FICTION',
                    'TV_MOVIE', 'THRILLER', 'WAR', 'WESTERN'
                ]);
            });

        // Fetch movies for the banner
        fetch(`${BASE_URL}/api/movies`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch movies');
                }
                return response.json();
            })
            .then(data => {
                // Filter movies with valid poster images
                const moviesWithPosters = data.filter(movie =>
                    movie.imgUrl &&
                    movie.imgUrl.trim() !== '' &&
                    !movie.imgUrl.includes('placeholder')
                );

                if (moviesWithPosters.length > 0) {
                    // Select one random movie for the banner
                    const randomIndex = Math.floor(Math.random() * moviesWithPosters.length);
                    setBannerMovie(moviesWithPosters[randomIndex]);
                }
            })
            .catch(error => {
                console.error('Error fetching banner movie:', error);
            });
    }, [BASE_URL]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        // Build query string with all filters
        const params = new URLSearchParams();
        if (formData.title) params.append('title', formData.title);
        if (formData.director) params.append('director', formData.director);
        if (formData.cast) params.append('cast', formData.cast);
        if (formData.year) params.append('year', formData.year);
        if (formData.genre) params.append('genre', formData.genre);

        // Make API call to backend
        fetch(`${BASE_URL}/api/movies/search?${params.toString()}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setLoading(false);
                // Navigate to the movie list page with search results
                navigate({
                    pathname: '/movies',
                    search: params.toString(),
                    state: { searchResults: data }
                });
            })
            .catch(error => {
                setLoading(false);
                setError('Error searching for movies. Please try again.');
                console.error('Error fetching search results:', error);
            });
    };

    return (
        <div className="home-page">
            <div
                className="hero-section"
                style={bannerMovie ? {
                    backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url(${bannerMovie.imgUrl})`,
                    backgroundSize: 'cover',
                    backgroundPosition: 'center top'
                } : {}}
            >
                <h1>Movie Feast</h1>
                <p>Discover your next favorite movie</p>
                {bannerMovie && (
                    <div className="featured-movie">
                        <p> {bannerMovie.title} ({bannerMovie.year})</p>
                    </div>
                )}
            </div>

            <div className="search-section">
                <h2>Find Movies</h2>
                {error && <div className="error-message">{error}</div>}

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
                                {genres.map(genre => {
                                    // Convert enum values to user-friendly display
                                    const displayName = genre.replace(/_/g, ' ').toLowerCase()
                                        .split(' ')
                                        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
                                        .join(' ');

                                    return (
                                        <option key={genre} value={genre}>
                                            {displayName}
                                        </option>
                                    );
                                })}
                            </select>
                        </div>
                    </div>

                    <div className="form-actions">
                        <button
                            type="submit"
                            className="search-button"
                            disabled={loading}
                        >
                            {loading ? 'Searching...' : 'Search Movies'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default HomePage;