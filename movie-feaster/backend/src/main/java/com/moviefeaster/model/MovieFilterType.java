package com.moviefeaster.Model;

import com.moviefeaster.Utils.MovieFilter;

/** Enum representing available movie filtering strategies. */
public enum MovieFilterType {
    TITLE_KEYWORD,
    EXACT_TITLE,
    DIRECTOR,
    ACTOR,
    GENRE,
    YEAR,
    YEAR_RANGE,
    MIN_RATING,
    MAX_RATING,
    COMMENT_KEYWORD,
    MIN_INAPP_RATING
}
