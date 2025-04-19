import React, { useState, useEffect, useCallback } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import MovieCard from '../components/MovieCard';
import './MovieListPage.css';

const MovieListPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

    const [movies, setMovies] = useState([]);
    const [displayedMovies, setDisplayedMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filters, setFilters] = useState({});
    const [sortOption, setSortOption] = useState('default');
    const [downloadFormat, setDownloadFormat] = useState('PRETTY');
    const [downloading, setDownloading] = useState(false);

    // Parse filters from URL parameters
    const parseFiltersFromUrl = useCallback(() => {
        const queryParams = new URLSearchParams(location.search);
        const filterParams = {};

        queryParams.forEach((value, key) => {
            // Only add non-empty values and ignore sort parameter
            if (key !== 'sort' && value && value.trim() !== '') {
                filterParams[key] = value;
            }
        });

        // Extract sort parameter if available
        const sortParam = queryParams.get('sort');
        if (sortParam) {
            setSortOption(sortParam);
        }

        return filterParams;
    }, [location.search]);

    // Load data with current filters
    const loadDataWithCurrentFilters = useCallback(() => {
        const currentFilters = parseFiltersFromUrl();
        setFilters(currentFilters);

        const queryParams = new URLSearchParams(location.search);
        fetchMovies(queryParams);
    }, [parseFiltersFromUrl, location.search]);

    useEffect(() => {
        // Check if we have search results from navigation state
        if (location.state && location.state.searchResults) {
            setMovies(location.state.searchResults);
            setDisplayedMovies(location.state.searchResults);
            setLoading(false);

            // Parse query parameters to display filters
            const filterParams = parseFiltersFromUrl();
            setFilters(filterParams);
            return;
        }

        // Load data based on URL parameters
        loadDataWithCurrentFilters();
    }, [location.search, location.state, loadDataWithCurrentFilters, parseFiltersFromUrl]);

    const fetchMovies = async (queryParams) => {
        try {
            setLoading(true);
            setError(null);

            try {
                // Step 1: First perform search with filters
                let searchEndpoint = `${BASE_URL}/api/movies/search`;

                // Create search params without the sort parameter
                const searchParams = new URLSearchParams();
                queryParams.forEach((value, key) => {
                    if (key !== 'sort' && value) {
                        searchParams.append(key, value);
                    }
                });

                // Add search params if they exist
                if (searchParams.toString()) {
                    searchEndpoint += `?${searchParams.toString()}`;
                }

                console.log('Fetching from search endpoint:', searchEndpoint);
                const searchResponse = await fetch(searchEndpoint);

                if (!searchResponse.ok) {
                    throw new Error(`Failed to fetch movies: ${searchResponse.status} ${searchResponse.statusText}`);
                }

                let data = await searchResponse.json();

                // Step 2: If sort parameter exists, sort the results
                const sortParam = queryParams.get('sort');
                if (sortParam && sortParam !== 'default') {
                    const backendSortParam = sortParam.replace('-', '_');
                    const sortEndpoint = `${BASE_URL}/api/movies/sort?sortType=${backendSortParam}`;

                    // Add any filter parameters to ensure consistency
                    queryParams.forEach((value, key) => {
                        if (key !== 'sort' && value) {
                            searchParams.append(key, value);
                        }
                    });

                    console.log('Fetching from sort endpoint:', sortEndpoint);
                    const sortResponse = await fetch(sortEndpoint);

                    if (sortResponse.ok) {
                        data = await sortResponse.json();
                    } else {
                        console.error('Sort request failed, using unsorted results');
                    }
                }

                setMovies(data);
                setDisplayedMovies(data);
            } catch (networkErr) {
                throw new Error(`Network error: ${networkErr.message || 'Could not connect to server'}`);
            }
        } catch (err) {
            setError(err.message);
            console.error('Error fetching movies:', err);
            setMovies([]);
            setDisplayedMovies([]);
        } finally {
            setLoading(false);
        }
    };

    const handleDownload = async () => {
        try {
            setDownloading(true);
            // Pass the current filters and sort option to ensure the download reflects what's displayed
            const params = new URLSearchParams(location.search);
            params.append('format', downloadFormat);

            const response = await fetch(`${BASE_URL}/api/movies/export?${params.toString()}`, {
                method: 'GET',
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Failed to download movie data: ${response.status} ${response.statusText}. ${errorText}`);
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
        } catch (err) {
            console.error('Error downloading file:', err);
            alert(`Failed to download file: ${err.message}`);
        } finally {
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
        const newSortOption = e.target.value;
        setSortOption(newSortOption);

        // Update URL with new sort parameter
        const queryParams = new URLSearchParams(location.search);

        if (newSortOption === 'default') {
            queryParams.delete('sort');
        } else {
            queryParams.set('sort', newSortOption);
        }

        // Navigate to the same page with updated query parameters
        navigate({
            pathname: location.pathname,
            search: queryParams.toString()
        });
    };

    // Function to format filter values for display
    const formatFilterValue = (key, value) => {
        if (!value) return '';

        if (key === 'genre') {
            try {
                // Convert SCIENCE_FICTION to Science Fiction
                return value.replace(/_/g, ' ').toLowerCase()
                    .split(' ')
                    .map(word => word.charAt(0).toUpperCase() + word.slice(1))
                    .join(' ');
            } catch (err) {
                console.error('Error formatting genre:', err);
                return value;
            }
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

    const handleRemoveFilter = (key) => {
        // Create a new URLSearchParams object from current location.search
        const queryParams = new URLSearchParams(location.search);

        // Remove the specified filter
        queryParams.delete(key);

        // Check if there are any remaining filters
        let hasRemainingFilters = false;
        queryParams.forEach((value, paramKey) => {
            if (paramKey !== 'sort' && value && value.trim() !== '') {
                hasRemainingFilters = true;
            }
        });

        // If no filters remain and we have a sort, keep the sort
        // Otherwise, use the search endpoint without sort
        if (!hasRemainingFilters) {
            // Only keep sort if it exists, otherwise URL will be clean
            if (!queryParams.has('sort')) {
                queryParams.delete('sort');
            }
        }

        // Update the URL with the new query parameters
        navigate({
            pathname: location.pathname,
            search: queryParams.toString()
        });
    };

    return (
        <div className="movie-list-page">
            <div className="list-header">
                <h1>Movie Results</h1>
                {Object.keys(filters).length > 0 && (
                    <div className="filter-summary">
                        <p>Filters:</p>
                        {Object.entries(filters).map(([key, value]) => (
                            value && (
                                <span key={key} className="filter-tag">
                                    <span className="filter-content">
                                        {getFilterDisplayName(key)}: {formatFilterValue(key, value)}
                                    </span>
                                    <span
                                        className="filter-remove"
                                        onClick={() => handleRemoveFilter(key)}
                                        role="button"
                                        tabIndex="0"
                                        aria-label={`Remove ${getFilterDisplayName(key)} filter`}
                                    >
                                        ×
                                    </span>
                                </span>
                            )
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
                                    <option value="rating-asc">Popularity (Low to High)</option>
                                    <option value="rating-desc">Popularity (High to Low)</option>
                                    {/*<option value="inapp-rating-asc">In-App Rating (Low to High)</option>*/}
                                    {/*<option value="inapp-rating-desc">In-App Rating (High to Low)</option>*/}
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
                                    key={movie.movieId}
                                    movie={movie}
                                    onClick={() => handleMovieClick(movie.movieId)}
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