import React from 'react';
import './MovieCard.css';

const MovieCard = ({ movie, onClick }) => {
    // Format genres for display
    const formatGenre = (genre) => {
        if (!genre) return '';
        // Convert SCIENCE_FICTION to Science Fiction
        return genre.replace(/_/g, ' ').toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    };

    // Display first genre if available
    const primaryGenre = movie.genres && movie.genres.length > 0
        ? formatGenre(movie.genres[0])
        : null;

    // Display first director if available
    const primaryDirector = movie.directors && movie.directors.length > 0
        ? movie.directors[0]
        : 'Unknown Director';

    return (
        <div className="movie-card" onClick={onClick}>
            <div className="movie-poster">
                {movie.imgUrl ? (
                    <img src={movie.imgUrl} alt={`${movie.title} poster`} />
                ) : (
                    <div className="placeholder-poster">
                        <span>{movie.title.charAt(0)}</span>
                    </div>
                )}

                {primaryGenre && <span className="movie-genre">{primaryGenre}</span>}
            </div>

            <div className="movie-info">
                <h3 className="movie-title">{movie.title}</h3>
                <div className="movie-meta">
                    <span className="movie-year">{movie.year}</span>
                    {movie.rating > 0 && (
                        <span className="movie-rating">
                            <span className="popularity-icon">👥</span>
                            {movie.rating.toFixed(0)}
                        </span>
                    )}
                </div>

                <div className="movie-directors">
                    Director: {primaryDirector}
                </div>

                <div className="movie-overview">
                    <p>{movie.overview && movie.overview.length > 120
                        ? `${movie.overview.substring(0, 120)}...`
                        : movie.overview || 'No description available.'}
                    </p>
                </div>
            </div>
        </div>
    );
};

export default MovieCard;