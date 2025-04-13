import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './MovieDetailPage.css';

const MovieDetailPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [movie, setMovie] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Separate states for comment and rating
    const [comment, setComment] = useState('');
    const [commentSubmitting, setCommentSubmitting] = useState(false);

    const [userRating, setUserRating] = useState(0);
    const [ratingSubmitting, setRatingSubmitting] = useState(false);

    const fetchMovieDetails = async () => {
        try {
            setLoading(true);

            // Try to get all movies and filter by id
            const response = await fetch('http://localhost:3000/api/movies/search');

            if (!response.ok) {
                throw new Error('Failed to fetch movies');
            }

            const movies = await response.json();
            const foundMovie = movies.find(movie => movie.id === parseInt(id));

            if (!foundMovie) {
                throw new Error('Movie not found');
            }

            setMovie(foundMovie);
            setLoading(false);
        } catch (err) {
            console.error('Error fetching movie:', err);
            setError(err.message);
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchMovieDetails();
    }, [id]);

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
            const response = await fetch(`http://localhost:3000/api/movies/${id}/comment`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(comment),
            });

            if (!response.ok) {
                throw new Error('Failed to submit comment');
            }

            // Clear the comment field after successful submission
            setComment('');
            alert('Comment submitted successfully!');

            // Refresh movie data to get updated comments
            await fetchMovieDetails();
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
            const response = await fetch(`http://localhost:3000/api/movies/${id}/rating`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userRating),
            });

            if (!response.ok) {
                throw new Error('Failed to submit rating');
            }

            alert('Rating submitted successfully!');
            setUserRating(0); // Reset rating input

            // Refresh movie data to get updated ratings
            await fetchMovieDetails();
        } catch (err) {
            console.error('Error submitting rating:', err);
            alert('Failed to submit rating. Please try again.');
        } finally {
            setRatingSubmitting(false);
        }
    };

    // Format genre for display
    const formatGenre = (genre) => {
        if (!genre) return '';

        // Handle if genre is a string or an object
        const genreName = typeof genre === 'string' ? genre : (genre.name || '');

        return genreName.replace(/_/g, ' ')
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    };

    if (loading) {
        return (
            <div className="movie-detail-page loading">
                <div className="loading-spinner"></div>
                <p>Loading movie details...</p>
            </div>
        );
    }

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

    return (
        <div className="movie-detail-page">
            <button className="back-button" onClick={handleGoBack}>
                ← Back to Results
            </button>

            <div className="movie-detail-container">
                <div className="movie-poster-section">
                    <div className="movie-poster">
                        {movie.imgUrl ? (
                            <img src={movie.imgUrl} alt={`${movie.title} poster`} />
                        ) : (
                            <div className="placeholder-poster">
                                <span>{movie.title.charAt(0)}</span>
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

                <div className="movie-info">
                    <h1 className="movie-title">
                        {movie.title} <span className="movie-year">({movie.year})</span>
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
                        <div className="detail-group">
                            <h3>Director{movie.directors && movie.directors.length > 1 ? 's' : ''}</h3>
                            <p>{movie.directors && movie.directors.length > 0
                                ? movie.directors.join(', ')
                                : 'Not available'}</p>
                        </div>

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

                        <div className="detail-group">
                            <h3>Overview</h3>
                            <p className="movie-description">{movie.overview || 'No description available.'}</p>
                        </div>

                        {/* In-App User Ratings section */}
                        <div className="detail-group">
                            <h3>In-App User Ratings</h3>
                            <div className="user-ratings">
                                {movie.InAppRating && movie.InAppRating.length > 0 ? (
                                    <div>
                                        <div className="rating-summary">
                                            <span className="average-rating">
                                                {(movie.InAppRating.reduce((sum, rating) => sum + rating, 0) / movie.InAppRating.length).toFixed(1)}
                                            </span>
                                            <div className="rating-stars">
                                                {[1, 2, 3, 4, 5].map((star) => {
                                                    const averageRating = movie.InAppRating.reduce((sum, rating) => sum + rating, 0) / movie.InAppRating.length;
                                                    return (
                                                        <span
                                                            key={star}
                                                            className={`star ${averageRating >= star ? 'filled' : ''}`}
                                                        >
                                                            ★
                                                        </span>
                                                    );
                                                })}
                                            </div>
                                            <span className="rating-count">({movie.InAppRating.length} {movie.InAppRating.length === 1 ? 'rating' : 'ratings'})</span>
                                        </div>
                                    </div>
                                ) : (
                                    <p>No user ratings yet. Be the first to rate this movie!</p>
                                )}
                            </div>
                        </div>

                        {/* Comments section */}
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