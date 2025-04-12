import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './MovieDetailPage.css';

const MovieDetailPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [movie, setMovie] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [comment, setComment] = useState('');
    const [userRating, setUserRating] = useState(0);

    useEffect(() => {
        const fetchMovie = async () => {
            try {
                setLoading(true);
                const response = await fetch(`http://localhost:3000/api/movies/${id}`);

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

        fetchMovie();
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
            const response = await fetch(`http://localhost:8080/api/movies/${id}/comment`, {
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
        } catch (err) {
            console.error('Error submitting comment:', err);
            alert('Failed to submit comment. Please try again.');
        }
    };

    const handleRatingSubmit = async () => {
        if (userRating === 0) return;

        try {
            const response = await fetch(`http://localhost:8080/api/movies/${id}/rating`, {
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
        } catch (err) {
            console.error('Error submitting rating:', err);
            alert('Failed to submit rating. Please try again.');
        }
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
                <div className="movie-poster">
                    {movie.imgUrl ? (
                        <img src={movie.imgUrl} alt={`${movie.title} poster`} />
                    ) : (
                        <div className="placeholder-poster">
                            <span>{movie.title.charAt(0)}</span>
                        </div>
                    )}
                </div>

                <div className="movie-info">
                    <h1 className="movie-title">
                        {movie.title} <span className="movie-year">({movie.year})</span>
                    </h1>

                    <div className="movie-meta">
                        {movie.genres && movie.genres.length > 0 && (
                            <div className="movie-genres">
                                {movie.genres.map((genre, index) => (
                                    <span key={index} className="genre-item">{genre.name}</span>
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
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MovieDetailPage;