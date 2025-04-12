import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import MovieCard from '../components/MovieCard';
import './MovieListPage.css';

const MovieListPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filters, setFilters] = useState({});

    useEffect(() => {
        // Check if we have search results from navigation state
        if (location.state && location.state.searchResults) {
            setMovies(location.state.searchResults);
            setLoading(false);
            return;
        }

        // Parse query parameters from URL
        const queryParams = new URLSearchParams(location.search);
        const filterParams = {};

        queryParams.forEach((value, key) => {
            filterParams[key] = value;
        });

        setFilters(filterParams);

        // Fetch movies based on filters
        fetchMovies(filterParams);
    }, [location.search, location.state]);

    const fetchMovies = async (filterParams) => {
        try {
            setLoading(true);

            // Build API URL with query parameters
            const apiUrl = new URL('http://localhost:3000/api/movies/search');
            Object.entries(filterParams).forEach(([key, value]) => {
                if (value) {
                    apiUrl.searchParams.append(key, value);
                }
            });

            const response = await fetch(apiUrl);
            if (!response.ok) {
                throw new Error('Failed to fetch movies');
            }

            const data = await response.json();
            setMovies(data);
            setLoading(false);
        } catch (err) {
            setError(err.message);
            setLoading(false);
        }
    };

    const handleMovieClick = (movieId) => {
        navigate(`/movies/${movieId}`);
    };

    // Function to get the display name for filter keys
    const getFilterDisplayName = (key) => {
        const displayNames = {
            title: 'Title',
            director: 'Director',
            cast: 'Cast',
            year: 'Year',
            genre: 'Genre'
        };
        return displayNames[key] || key;
    };

    return (
        <div className="movie-list-page">
            <div className="list-header">
                <h1>Movie Results</h1>
                <div className="filter-summary">
                    {Object.entries(filters).map(([key, value]) => (
                        value && <span key={key} className="filter-tag">
                            {getFilterDisplayName(key)}: {value}
                        </span>
                    ))}
                </div>
            </div>

            {error && <div className="error-message">{error}</div>}

            {loading ? (
                <div className="loading-container">
                    <div className="loading-spinner"></div>
                    <p>Loading movies...</p>
                </div>
            ) : (
                <>
                    <div className="results-count">
                        <p>Found {movies.length} movies</p>
                    </div>

                    {movies.length === 0 ? (
                        <div className="no-results">
                            <h2>No movies found</h2>
                            <p>Try adjusting your search criteria</p>
                        </div>
                    ) : (
                        <div className="movie-grid">
                            {movies.map(movie => (
                                <MovieCard
                                    key={movie.id}
                                    movie={movie}
                                    onClick={() => handleMovieClick(movie.id)}
                                />
                            ))}
                        </div>
                    )}
                </>
            )}
        </div>
    );
};

export default MovieListPage;