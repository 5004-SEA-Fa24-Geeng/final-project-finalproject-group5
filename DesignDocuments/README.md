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
    +getParsedMoviesSummary() Collection~MovieSummary~
}
class MovieFeasterApplication {
    +main(String[]) void
}

%% === UTILS ===
class DataFormatter {
    +formatSingleMovie(Movie) String
    +formatMovieList(Collection<Movie>) String
    +write(Collection<Movie>, Format, OutputStream) void
    -writeXmlData(Collection<Movie>, OutputStream) void
    -writeJsonData(Collection<Movie>, OutputStream) void
    -writeCsvData(Collection<Movie>, OutputStream) void
    -joinDirectors(Movie) String
    -joinGenres(Movie) String
    -joinCastings(Movie) String
    -joinComments(Movie) String
    -formatCsvField(String) String
}
class MovieXMLWrapper {
    -Collection<Movie> movie
    +MovieXMLWrapper()
    +MovieXMLWrapper(Collection<Movie>)
    +getMovie() Collection<Movie>
    +setMovie(Collection<Movie>) void
}
class NetUtil {
  +getTop50MoviesJson() InputStream
  +getCrewJsonStream(int) InputStream
}
class MovieFilterFacilitator {
  +applyFilters(List~Movie~, Map~MovieFilterType, Object~) List~Movie~
}
class MovieFilter {
    -MovieFilter()
    +filterByTitle(List<Movie>, String) List<Movie>
    +filterByExactTitle(List<Movie>, String) List<Movie>
    +filterByDirector(List<Movie>, String) List<Movie>
    +filterByYear(List<Movie>, int) List<Movie>
    +filterByYearRange(List<Movie>, int, int) List<Movie>
    +filterByMinRating(List<Movie>, float) List<Movie>
    +filterByMaxRating(List<Movie>, float) List<Movie>
    +filterByGenre(List<Movie>, String) List<Movie>
    +filterByActor(List<Movie>, String) List<Movie>
    +filterByCommentKeyword(List<Movie>, String) List<Movie>
    +filterByMinInAppRating(List<Movie>, double) List<Movie>
    +combineAnd(List<Movie>, List<Movie>) List<Movie>
}
class MovieSorter {
    -MovieSorter()
    +sortByTitle(List<Movie>) List<Movie>
    +sortByTitleDescending(List<Movie>) List<Movie>
    +sortByRating(List<Movie>) List<Movie>
    +sortByRatingAscending(List<Movie>) List<Movie>
    +sortByYear(List<Movie>) List<Movie>
    +sortByYearAscending(List<Movie>) List<Movie>
    +sortByInAppRating(List<Movie>) List<Movie>
    +sortByInAppRatingAscending(List<Movie>) List<Movie>
    +getTopN(List<Movie>, int) List<Movie>
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
MovieModel --> MovieFilter
MovieModel --> Movie
MovieModel --> DataFormatter
Movie --> Genre
Movie --> Format
MovieParser --> NetUtil
DataFormatter --> Format
DataFormatter --> MovieXMLWrapper
MovieFeasterApplication --> MovieController

```
