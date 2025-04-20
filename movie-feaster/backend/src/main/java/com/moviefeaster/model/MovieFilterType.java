package com.moviefeaster.model;

/** Enum representing available movie filtering strategies. */
public enum MovieFilterType {
    /** Filter by keyword in movie title. */
    TITLE_KEYWORD,
    
    /** Filter by exact movie title match. */
    EXACT_TITLE,
    
    /** Filter by director name. */
    DIRECTOR,
    
    /** Filter by actor name. */
    ACTOR,
    
    /** Filter by genre. */
    GENRE,
    
    /** Filter by release year. */
    YEAR,
    
    /** Filter by year range. */
    YEAR_RANGE,
    
    /** Filter by minimum rating. */
    MIN_RATING,
    
    /** Filter by maximum rating. */
    MAX_RATING,
    
    /** Filter by keyword in comments. */
    COMMENT_KEYWORD,
    
    /** Filter by minimum in-app rating. */
    MIN_INAPP_RATING
}
