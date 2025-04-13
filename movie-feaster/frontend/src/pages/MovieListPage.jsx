import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import MovieCard from '../components/MovieCard';
import './MovieListPage.css';

const MovieListPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [movies, setMovies] = useState([]);
    const [displayedMovies, setDisplayedMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filters, setFilters] = useState({});
    const [sortOption, setSortOption] = useState('default');
    const [downloadFormat, setDownloadFormat] = useState('PRETTY');
    const [downloading, setDownloading] = useState(false);

    useEffect(() => {
        // Check if we have search results from navigation state
        if (location.state && location.state.searchResults) {
            setMovies(location.state.searchResults);
            setDisplayedMovies(location.state.searchResults);
            setLoading(false);

            // Parse query parameters to display filters
            const queryParams = new URLSearchParams(location.search);
            const filterParams = {};

            queryParams.forEach((value, key) => {
                filterParams[key] = value;
            });

            setFilters(filterParams);
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
        fetchMovies(queryParams);
    }, [location.search, location.state]);

    // Apply sorting whenever sort option or movies change
    useEffect(() => {
        if (movies.length > 0) {
            const sortedMovies = sortMovies(movies, sortOption);
            setDisplayedMovies(sortedMovies);
        }
    }, [sortOption, movies]);

    const fetchMovies = async (queryParams) => {
        try {
            setLoading(true);
            setError(null);

            const response = await fetch(`http://localhost:3000/api/movies/search?${queryParams.toString()}`);

            if (!response.ok) {
                throw new Error('Failed to fetch movies');
            }

            const data = await response.json();
            setMovies(data);
            setDisplayedMovies(data);
            setLoading(false);
        } catch (err) {
            setError(err.message);
            setLoading(false);
            console.error('Error fetching movies:', err);
        }
    };

    const handleDownload = async () => {
        try {
            setDownloading(true);
            // Pass the current filters and sort option to ensure the download reflects what's displayed
            const params = new URLSearchParams(location.search);
            params.append('format', downloadFormat);

            const response = await fetch(`http://localhost:3000/api/movies/export?${params.toString()}`, {
                method: 'GET',
            });

            if (!response.ok) {
                throw new Error('Failed to download movie data');
            }

            // Handle file download
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = `movies_${downloadFormat.toLowerCase()}.${getFileExtension(downloadFormat)}`;
            document.body.appendChild(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);

            setDownloading(false);
        } catch (err) {
            console.error('Error downloading file:', err);
            alert('Failed to download file. Please try again.');
            setDownloading(false);
        }
    };

    // Helper function to get file extension based on format
    const getFileExtension = (format) => {
        switch(format) {
            case 'JSON': return 'json';
            case 'XML': return 'xml';
            case 'CSV': return 'csv';
            default: return 'txt';
        }
    };

    const handleMovieClick = (movieId) => {
        navigate(`/movies/${movieId}`);
    };

    const handleSortChange = (e) => {
        setSortOption(e.target.value);
    };

    // Function to sort movies based on selected option
    const sortMovies = (movieList, option) => {
        const moviesCopy = [...movieList];

        switch(option) {
            case 'title-asc':
                return moviesCopy.sort((a, b) => a.title.localeCompare(b.title));
            case 'title-desc':
                return moviesCopy.sort((a, b) => b.title.localeCompare(a.title));
            case 'year-asc':
                return moviesCopy.sort((a, b) => a.year - b.year);
            case 'year-desc':
                return moviesCopy.sort((a, b) => b.year - a.year);
            case 'rating-asc':
                return moviesCopy.sort((a, b) => a.rating - b.rating);
            case 'rating-desc':
                return moviesCopy.sort((a, b) => b.rating - a.rating);
            case 'inapp-rating-asc':
                return moviesCopy.sort((a, b) => {
                    const aRating = a.InAppRating && a.InAppRating.length > 0 ?
                        a.InAppRating.reduce((sum, rating) => sum + rating, 0) / a.InAppRating.length : 0;
                    const bRating = b.InAppRating && b.InAppRating.length > 0 ?
                        b.InAppRating.reduce((sum, rating) => sum + rating, 0) / b.InAppRating.length : 0;
                    return aRating - bRating;
                });
            case 'inapp-rating-desc':
                return moviesCopy.sort((a, b) => {
                    const aRating = a.InAppRating && a.InAppRating.length > 0 ?
                        a.InAppRating.reduce((sum, rating) => sum + rating, 0) / a.InAppRating.length : 0;
                    const bRating = b.InAppRating && b.InAppRating.length > 0 ?
                        b.InAppRating.reduce((sum, rating) => sum + rating, 0) / b.InAppRating.length : 0;
                    return bRating - aRating;
                });
            default:
                return moviesCopy;
        }
    };

    // Function to format filter values for display
    const formatFilterValue = (key, value) => {
        if (key === 'genre') {
            // Convert SCIENCE_FICTION to Science Fiction
            return value.replace(/_/g, ' ').toLowerCase()
                .split(' ')
                .map(word => word.charAt(0).toUpperCase() + word.slice(1))
                .join(' ');
        }
        return value;
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
                {Object.keys(filters).length > 0 && (
                    <div className="filter-summary">
                        <p>Filters:</p>
                        {Object.entries(filters).map(([key, value]) => (
                            value && <span key={key} className="filter-tag">
                                {getFilterDisplayName(key)}: {formatFilterValue(key, value)}
                            </span>
                        ))}
                    </div>
                )}
            </div>

            {error && <div className="error-message">{error}</div>}

            {loading ? (
                <div className="loading-container">
                    <div className="loading-spinner"></div>
                    <p>Loading movies...</p>
                </div>
            ) : (
                <>
                    <div className="results-controls">
                        <div className="results-count">
                            <p>Found {movies.length} movies</p>
                        </div>
                        <div className="controls-container">
                            <div className="sort-control">
                                <label htmlFor="sort-select">Sort by:</label>
                                <select
                                    id="sort-select"
                                    value={sortOption}
                                    onChange={handleSortChange}
                                    className="sort-select"
                                >
                                    <option value="default">Default</option>
                                    <option value="title-asc">Title (A-Z)</option>
                                    <option value="title-desc">Title (Z-A)</option>
                                    <option value="year-asc">Year (Oldest First)</option>
                                    <option value="year-desc">Year (Newest First)</option>
                                    <option value="rating-asc">Rating (Low to High)</option>
                                    <option value="rating-desc">Rating (High to Low)</option>
                                    <option value="inapp-rating-asc">In-App Rating (Low to High)</option>
                                    <option value="inapp-rating-desc">In-App Rating (High to Low)</option>
                                </select>
                            </div>
                            <div className="download-control">
                                <label htmlFor="download-format">Download:</label>
                                <select
                                    id="download-format"
                                    value={downloadFormat}
                                    onChange={(e) => setDownloadFormat(e.target.value)}
                                    className="download-select"
                                >
                                    <option value="PRETTY">Text</option>
                                    <option value="JSON">JSON</option>
                                    <option value="XML">XML</option>
                                    <option value="CSV">CSV</option>
                                </select>
                                <button
                                    className="download-button"
                                    onClick={handleDownload}
                                    disabled={downloading || movies.length === 0}
                                >
                                    {downloading ? 'Downloading...' : 'Download'}
                                </button>
                            </div>
                        </div>
                    </div>

                    {displayedMovies.length === 0 ? (
                        <div className="no-results">
                            <h2>No movies found</h2>
                            <p>Try adjusting your search criteria</p>
                        </div>
                    ) : (
                        <div className="movie-grid">
                            {displayedMovies.map(movie => (
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