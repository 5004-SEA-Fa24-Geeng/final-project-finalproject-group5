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

    // Helper functions
    const calculateAverageRating = useCallback((ratings) => {
        if (!ratings || ratings.length === 0) return 0;
        return ratings.reduce((sum, rating) => sum + rating, 0) / ratings.length;
    }, []);

    const formatGenre = useCallback((genre) => {
        if (!genre) return '';
        const genreName = typeof genre === 'string' ? genre : (genre.name || '');
        return genreName.replace(/_/g, ' ')
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }, []);

    // Data fetching
    const fetchMovie = async () => {
        try {
            setLoading(true);
            const response = await fetch(`${BASE_URL}/api/movies/${id}`);

            if (!response.ok) {
                throw new Error('Failed to fetch movie details');
            }

            const data = await response.json();
            setMovie(data);
            setLoading(false);
        } catch (err) {
            setError(err.message);
            setLoading(false);
        }
    };
    useEffect(() => {
        fetchMovie();
    }, [id]);
    // Event handlers
    const handleGoBack = () => {
        navigate(-1);
    };

    const handleCommentChange = (e) => {
        setComment(e.target.value);
    };

    const handleRatingChange = (value) => {
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
            alert('Comment submitted successfully!');
            await fetchMovie();
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

        try {
            setRatingSubmitting(true);
            const response = await fetch(`${BASE_URL}/api/movies/${id}/rating`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ rating: userRating }),
            });

            if (!response.ok) {
                throw new Error('Failed to submit rating');
            }

            alert('Rating submitted successfully!');
            await fetchMovie();
        } catch (err) {
            console.error('Error submitting rating:', err);
            alert('Failed to submit rating. Please try again.');
        } finally {
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

    // Calculate ratings for UI
    const averageInAppRating = movie.InAppRating ?
        calculateAverageRating(movie.InAppRating) : 0;

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
                                {movie.InAppRating && movie.InAppRating.length > 0 ? (
                                    <div>
                                        <div className="rating-summary">
                                            <span className="average-rating">
                                                {averageInAppRating.toFixed(1)}
                                            </span>
                                            <div className="rating-stars">
                                                {[1, 2, 3, 4, 5].map((star) => (
                                                    <span
                                                        key={star}
                                                        className={`star ${
                                                            averageInAppRating >= star - 0.25 ? 'filled' :
                                                                averageInAppRating >= star - 0.75 ? 'half-filled' : ''
                                                        }`}
                                                    >
                                                        ★
                                                    </span>
                                                ))}
                                            </div>
                                            <span className="rating-count">
                                                ({movie.InAppRating.length} {movie.InAppRating.length === 1 ? 'rating' : 'ratings'})
                                            </span>
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
                                        {movie.comments.map((comment, index) => (
                                            <li key={index} className="comment-item">{comment}</li>
                                        ))}
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