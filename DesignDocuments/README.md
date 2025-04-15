# Design Documents

You may have multiple design documents for this project. Place them all in this folder. File naming is up to you, but it should be clear what the document is about. At the bare minimum, you will want a pre/post UML diagram for the project. 

```mermaid

classDiagram

%% === CONTROLLER ===
class MovieController {
  +handleMultiFilterSearch(String, String, String, String, String) List~Movie~
  +handleSort(String) List~Movie~
  +handleCommentSubmission(int, String) String
  +handleRatingSubmission(int, double) void
  +getAllGenres() List~String~
  +getAllMovies() List~Movie~
  +getMovieById(int) Movie
  +exportMovies(String) ResponseEntity~byte[]~
}
class MovieControllerInterface {
  +handleMultiFilterSearch(String, String, String, String, String) List~Movie~
  +handleSort(String) List~Movie~
  +handleCommentSubmission(int, String) String
  +handleRatingSubmission(int, double) void
}
class InputProcessor {
  +optionalParseTitle(String) String
  +optionalParseDirector(String) String
  +optionalParseCast(String) String
  +optionalParseYear(String) Integer
  +optionalParseGenre(String) Genre
}
class InputProcessorInterface {
  +optionalParseTitle(String) String
  +optionalParseDirector(String) String
  +optionalParseCast(String) String
  +optionalParseYear(String) Integer
  +optionalParseGenre(String) Genre
}

%% === MODEL ===
class Movie {
  -String title
  -List~String~ directors
  -int year
  -float rating
  -List~Genre~ genres
  -List~String~ castings
  -List~String~ comments
  -List~Float~ inAppRating
  -Pair~Float, Integer~ updatedAverageInAppRating
  -String imgUrl
  +getId() int
  +getTitle() String
  +getDirectors() List~String~
  +getYear() int
  +getRating() float
  +getGenres() List~Genre~
  +getCastings() List~String~
  +getComments() List~String~
  +getInAppRating() List~Float~
  +getUpdatedAverageInAppRating() Pair~Float, Integer~
}
class Format {
  <<enumeration>>
  JSON
  XML
  CSV
  PRETTY
  +containsValues(String) Format
}
class Genre {
  <<enumeration>>
  ACTION
  ADVENTURE
  ANIMATION
  COMEDY
  CRIME
  DOCUMENTARY
  DRAMA
  FAMILY
  FANTASY
  HISTORY
  HORROR
  MUSIC
  MYSTERY
  ROMANCE
  SCIENCE_FICTION
  TV_MOVIE
  THRILLER
  WAR
  WESTERN
  +fromId(int) Genre
  +fromName(String) Genre
  +getId() int
}
class MovieFilterType {
  <<enumeration>>
  TITLE_KEYWORD
  DIRECTOR
  ACTOR
  YEAR
  GENRE
}
class MovieSorterType {
  <<enumeration>>
  TITLE_AZ
  TITLE_ZA
  RATING_ASC
  RATING_DESC
  YEAR_ASC
  YEAR_DESC
  +fromValue(String) MovieSorterType
}

%% === SERVICE ===
class MovieModel {
  +fetchMovies() void
  +getMovies() List~Movie~
  +getProcessedMovies() List~Movie~
  +searchByFilter(boolean, Map~MovieFilterType, Object~) void
  +sortMovieList(MovieSorterType) void
  +UpdateComments(int, String) void
  +UpdateRating(int, double) void
}
class MovieModelInterface {
  +fetchMovies() void
  +getMovies() List~Movie~
  +getProcessedMovies() List~Movie~
  +searchByFilter(boolean, Map~MovieFilterType, Object~) void
  +sortMovieList(MovieSorterType) void
  +UpdateComments(int, String) void
  +UpdateRating(int, double) void
}
class MovieParser {
  +getMoviesFromApi() List~Movie~
}
class MovieFeasterApplication {
  +main(String[]) void
}

%% === UTILS ===
class DataFormatter {
  +write(Collection~Movie~, Format, OutputStream) void
}
class MovieXMLWrapper {
  +getMovie() Collection~Movie~
  +setMovie(Collection~Movie~) void
}
class NetUtil {
  +getTop50MoviesJson() InputStream
  +getCrewJsonStream(int) InputStream
}
class MovieFilterFacilitator {
  +applyFilters(List~Movie~, Map~MovieFilterType, Object~) List~Movie~
}
class MovieFilter {
  +filter(List~Movie~, MovieFilterType, Object) List~Movie~
}
class MovieSorter {
  +sort(List~Movie~, MovieSorterType) void
}

%% === RELATIONSHIPS ===
MovieController --> MovieModel
MovieController --> InputProcessor
MovieController --> MovieFilterType
MovieController --> MovieSorterType
MovieController --> MovieControllerInterface
InputProcessor --> InputProcessorInterface
InputProcessor --> Genre
MovieModel --> MovieModelInterface
MovieModel --> MovieParser
MovieModel --> MovieFilterFacilitator
MovieModel --> MovieSorter
MovieModel --> Movie
Movie --> Genre
Movie --> Format
MovieParser --> NetUtil
DataFormatter --> Format
DataFormatter --> MovieXMLWrapper
MovieFeasterApplication --> MovieController

```
