import React, { useState, useEffect, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './MovieDetailPage.css';

const MovieDetailPage = () => {
    // Constants
    const BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

    // Hooks
    const { id } = useParams();
    const navigate = useNavigate();

    // State
    const [movie, setMovie] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [comment, setComment] = useState('');
    const [commentSubmitting, setCommentSubmitting] = useState(false);
    const [userRating, setUserRating] = useState(0);
    const [ratingSubmitting, setRatingSubmitting] = useState(false);
    const [hasRated, setHasRated] = useState(false); // Track if user has already rated
    const [refreshing, setRefreshing] = useState(false); // Track when we're refreshing data
    const [ratingCount, setRatingCount] = useState(0); // Track rating count locally

    // Helper functions
    const formatGenre = useCallback((genre) => {
        if (!genre) return '';
        const genreName = typeof genre === 'string' ? genre : (genre.name || '');
        return genreName.replace(/_/g, ' ')
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }, []);

    // Check if user has already rated this movie and retrieve their rating
    const checkUserHasRated = useCallback(() => {
        // In a real app, you would check against the user ID
        // For now, we'll use localStorage to track ratings by movie ID
        const ratedMovies = JSON.parse(localStorage.getItem('ratedMovies') || '[]');
        const hasAlreadyRated = ratedMovies.includes(parseInt(id));
        setHasRated(hasAlreadyRated);

        // If user has rated, get their stored rating value
        if (hasAlreadyRated) {
            const storedUserRating = localStorage.getItem(`movieUserRating_${id}`);
            if (storedUserRating) {
                setUserRating(parseInt(storedUserRating));
            }
        }

        // Also retrieve stored rating count
        const storedCount = localStorage.getItem(`movieRatingCount_${id}`);
        if (storedCount) {
            setRatingCount(parseInt(storedCount));
        }
    }, [id]);

    // Data fetching
    const fetchMovie = useCallback(async (isRefresh = false) => {
        try {
            // Only show loading spinner for initial load, not for refreshes
            if (!isRefresh) {
                setLoading(true);
            } else {
                setRefreshing(true);
            }

            // Add a cache-busting parameter to avoid getting cached responses
            const cacheParam = `_nocache=${Date.now()}`;
            const response = await fetch(`${BASE_URL}/api/movies/${id}?${cacheParam}`);

            if (!response.ok) {
                throw new Error('Failed to fetch movie details');
            }

            const data = await response.json();
            console.log('Fetched movie data:', data); // Log the fetched data to debug

            // Update state with new data
            setMovie(data);

            // Clear appropriate loading state
            if (!isRefresh) {
                setLoading(false);
            } else {
                setRefreshing(false);
            }

            // Check if user has rated after fetching movie
            checkUserHasRated();
        } catch (err) {
            console.error('Error fetching movie:', err);
            setError(err.message);
            if (!isRefresh) {
                setLoading(false);
            } else {
                setRefreshing(false);
            }
        }
    }, [BASE_URL, id, checkUserHasRated]);

    useEffect(() => {
        fetchMovie();
    }, [fetchMovie]);

    // Event handlers
    const handleGoBack = () => {
        navigate(-1);
    };

    const handleCommentChange = (e) => {
        setComment(e.target.value);
    };

    const handleRatingChange = (value) => {
        if (hasRated) return; // Prevent rating change if already rated
        setUserRating(value);
    };

    const handleCommentSubmit = async (e) => {
        e.preventDefault();
        if (!comment.trim()) return;

        try {
            setCommentSubmitting(true);
            const response = await fetch(`${BASE_URL}/api/movies/${id}/comment`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ comment }),
            });

            if (!response.ok) {
                throw new Error('Failed to submit comment');
            }

            setComment('');
            await fetchMovie(true); // Use refresh mode for comment updates too
            alert('Comment submitted successfully!');
        } catch (err) {
            console.error('Error submitting comment:', err);
            alert('Failed to submit comment. Please try again.');
        } finally {
            setCommentSubmitting(false);
        }
    };

    const handleRatingSubmit = async () => {
        if (userRating === 0) {
            alert('Please select a rating first');
            return;
        }

        if (hasRated) {
            alert('You have already rated this movie');
            return;
        }

        try {
            setRatingSubmitting(true);

            // Looking at the MovieController.java, the endpoint expects a double value
            const response = await fetch(`${BASE_URL}/api/movies/${id}/rating`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: userRating.toString(), // Send the rating as a string value
            });

            if (!response.ok) {
                throw new Error('Failed to submit rating');
            }

            // Store that user has rated this movie
            const ratedMovies = JSON.parse(localStorage.getItem('ratedMovies') || '[]');
            ratedMovies.push(parseInt(id));
            localStorage.setItem('ratedMovies', JSON.stringify(ratedMovies));

            // Store the user's rating value for this movie
            localStorage.setItem(`movieUserRating_${id}`, userRating.toString());

            // Store rating count locally (temporary solution until backend provides it)
            const currentCount = parseInt(localStorage.getItem(`movieRatingCount_${id}`) || '0');
            const newCount = currentCount + 1;
            localStorage.setItem(`movieRatingCount_${id}`, newCount.toString());
            setRatingCount(newCount);

            // Update UI to show user has rated
            setHasRated(true);

            // We don't reset userRating so the stars will stay filled
            // setUserRating(0);

            // Small delay to ensure backend has processed the rating
            // before we try to fetch the updated data
            setTimeout(async () => {
                try {
                    // Fetch updated movie data (including new ratings)
                    // Pass true to indicate this is a refresh, not initial load
                    await fetchMovie(true);

                    // Show success message AFTER data is refreshed
                    alert('Rating submitted successfully!');
                } finally {
                    setRatingSubmitting(false);
                }
            }, 300); // 300ms delay
        } catch (err) {
            console.error('Error submitting rating:', err);
            alert('Failed to submit rating. Please try again.');
            setRatingSubmitting(false);
        }
    };

    // Render loading state
    if (loading) {
        return (
            <div className="movie-detail-page loading">
                <div className="loading-spinner"></div>
                <p>Loading movie details...</p>
            </div>
        );
    }

    // Render error state
    if (error) {
        return (
            <div className="movie-detail-page error">
                <div className="error-message">
                    <h2>Error</h2>
                    <p>{error}</p>
                    <button className="back-button" onClick={handleGoBack}>
                        Go Back
                    </button>
                </div>
            </div>
        );
    }

    // Render not found state
    if (!movie) {
        return (
            <div className="movie-detail-page not-found">
                <h2>Movie Not Found</h2>
                <p>The movie you're looking for doesn't exist or has been removed.</p>
                <button className="back-button" onClick={handleGoBack}>
                    Go Back
                </button>
            </div>
        );
    }

    // Main render
    return (
        <div className="movie-detail-page">
            <button className="back-button" onClick={handleGoBack}>
                ← Back to Results
            </button>

            <div className="movie-detail-container">
                {/* Poster and Rating Section */}
                <div className="movie-poster-section">
                    <div className="movie-poster">
                        {movie.imgUrl ? (
                            <img src={movie.imgUrl} alt={`${movie.title} poster`} />
                        ) : (
                            <div className="placeholder-poster">
                                <span>{movie.title ? movie.title.charAt(0) : 'M'}</span>
                            </div>
                        )}
                    </div>

                    <div className="rating-section">
                        <h3>Rate This Movie</h3>
                        {hasRated ? (
                            <div className="already-rated-message">
                                <p>Thank you for rating this movie!</p>
                                <div className="star-rating">
                                    {[1, 2, 3, 4, 5].map((star) => (
                                        <span
                                            key={star}
                                            className={`rating-star disabled ${userRating >= star ? 'active' : ''}`}
                                        >
                                            ★
                                        </span>
                                    ))}
                                </div>
                            </div>
                        ) : (
                            <>
                                <div className="star-rating">
                                    {[1, 2, 3, 4, 5].map((star) => (
                                        <span
                                            key={star}
                                            className={`rating-star ${userRating >= star ? 'active' : ''}`}
                                            onClick={() => handleRatingChange(star)}
                                        >
                                            ★
                                        </span>
                                    ))}
                                </div>
                                <button
                                    className="rating-button"
                                    onClick={handleRatingSubmit}
                                    disabled={ratingSubmitting || userRating === 0}
                                >
                                    {ratingSubmitting ? 'Submitting...' : 'Submit Rating'}
                                </button>
                            </>
                        )}
                    </div>
                </div>

                {/* Movie Information Section */}
                <div className="movie-info">
                    <h1 className="movie-title">
                        {movie.title} {movie.year && <span className="movie-year">({movie.year})</span>}
                    </h1>

                    <div className="movie-meta">
                        {movie.genres && movie.genres.length > 0 && (
                            <div className="movie-genres">
                                {movie.genres.map((genre, index) => (
                                    <span key={index} className="genre-item">
                                        {formatGenre(genre)}
                                    </span>
                                ))}
                            </div>
                        )}
                        {movie.rating > 0 && (
                            <div className="movie-rating">
                                <span className="star-icon">★</span>
                                <span>{movie.rating.toFixed(1)}/10</span>
                            </div>
                        )}
                    </div>

                    <div className="movie-details">
                        {/* Directors */}
                        <div className="detail-group">
                            <h3>Director{movie.directors && movie.directors.length > 1 ? 's' : ''}</h3>
                            <p>
                                {movie.directors && movie.directors.length > 0
                                    ? movie.directors.join(', ')
                                    : 'Not available'}
                            </p>
                        </div>

                        {/* Cast */}
                        {movie.castings && movie.castings.length > 0 && (
                            <div className="detail-group">
                                <h3>Cast</h3>
                                <div className="movie-cast">
                                    {movie.castings.map((actor, index) => (
                                        <span key={index} className="cast-item">{actor}</span>
                                    ))}
                                </div>
                            </div>
                        )}

                        {/* Overview */}
                        <div className="detail-group">
                            <h3>Overview</h3>
                            <p className="movie-description">{movie.overview || 'No description available.'}</p>
                        </div>

                        {/* User Ratings */}
                        <div className="detail-group">
                            <h3>In-App User Ratings</h3>
                            <div className="user-ratings">
                                {refreshing ? (
                                    <div className="refreshing-ratings">
                                        <p>Updating ratings...</p>
                                        <div className="mini-spinner"></div>
                                    </div>
                                ) : movie.InAppRating > 0 ? (
                                    <div className="rating-summary">
                                        <p>App Rating: {parseFloat(movie.InAppRating).toFixed(1)} (Total ratings: {ratingCount || '?'})</p>
                                        <div className="rating-stars">
                                            {[1, 2, 3, 4, 5].map((star) => (
                                                <span
                                                    key={star}
                                                    className={`star ${
                                                        movie.InAppRating >= star ? 'filled' : ''
                                                    }`}
                                                >
                                                    ★
                                                </span>
                                            ))}
                                        </div>
                                    </div>
                                ) : (
                                    <p>No user ratings yet. Be the first to rate this movie!</p>
                                )}
                            </div>
                        </div>

                        {/* Comments */}
                        <div className="detail-group">
                            <h3>Comments</h3>
                            <div className="comments-section">
                                {movie.comments && movie.comments.length > 0 ? (
                                    <ul className="comments-list">
                                        {movie.comments.map((comment, index) => {
                                            // Clean up comment format - remove curly braces and format properly
                                            let cleanComment = comment;

                                            // Handle different possible formats of comments
                                            if (typeof comment === 'string') {
                                                // If the comment is a JSON string, parse it and extract content
                                                if (comment.trim().startsWith('{') && comment.trim().endsWith('}')) {
                                                    try {
                                                        const parsedComment = JSON.parse(comment);
                                                        if (parsedComment && typeof parsedComment === 'object') {
                                                            // If we have a 'comment' property, use that
                                                            cleanComment = parsedComment.comment || comment;
                                                        }
                                                    } catch (e) {
                                                        // If parsing fails, just clean up the string
                                                        cleanComment = comment.replace(/[{}"\s]*(comment:|comment=)?["\s]*/g, '').trim();
                                                    }
                                                } else {
                                                    // Otherwise just remove any curly braces that might be present
                                                    cleanComment = comment.replace(/[{}]/g, '').trim();
                                                }
                                            }

                                            return (
                                                <li key={index} className="comment-item">
                                                    {cleanComment}
                                                </li>
                                            );
                                        })}
                                    </ul>
                                ) : (
                                    <p>No comments yet. Be the first to comment on this movie!</p>
                                )}

                                <form className="comment-form" onSubmit={handleCommentSubmit}>
                                    <textarea
                                        className="comment-input"
                                        placeholder="Add your comment..."
                                        value={comment}
                                        onChange={handleCommentChange}
                                        rows={4}
                                        required
                                    ></textarea>
                                    <button
                                        type="submit"
                                        className="comment-button"
                                        disabled={commentSubmitting || !comment.trim()}
                                    >
                                        {commentSubmitting ? 'Submitting...' : 'Add Comment'}
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MovieDetailPage;