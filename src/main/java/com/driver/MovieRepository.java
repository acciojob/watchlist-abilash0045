package com.driver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Pair{
    Movie movie;
    Director director;
    Pair(Movie movie,Director director){
        this.movie = movie;
        this.director = director;
    }
}
@Repository
public class MovieRepository {
    HashMap<String,Movie> movieHashMap = new HashMap<>();
    HashMap<String,Director> directorHashMap = new HashMap<>();
    HashMap<Director,List<Movie>> pairHashMap = new HashMap<>();
    public String  addMovie(Movie movie){
        movieHashMap.put(movie.getName(),movie);
        return "Movie added successfully";
    }
    public String addDirector(Director director){
        directorHashMap.put(director.getName(),director);
        return "Director added successfully";
    }
    public String addMovieDirectorPair(String movieName,String directorName){
        if (pairHashMap.containsKey(directorHashMap.get(directorName))){
            List<Movie> movie = pairHashMap.get(directorHashMap.get(directorName));
            movie.add(movieHashMap.get(movieName));
            pairHashMap.put(directorHashMap.get(directorName),movie);
        }else{
            List<Movie> list = new ArrayList<>();
            list.add(movieHashMap.get(movieName));
            pairHashMap.put(directorHashMap.get(directorName),list);
        }
        return "Pair added successfully";
    }
    public Movie getMovieByName(String movieName){
        return movieHashMap.get(movieName);
    }
    public Director getDirectorByName(String directorName){
        return directorHashMap.get(directorName);
    }
    public List<String> getMoviesByDirectorName(String directorName){
        List<String> movies = new ArrayList<>();
        for (Movie movie : pairHashMap.get(directorHashMap.get(directorName))){
            movies.add(movie.getName());
        }
        return movies;
    }
    public List<String> findAllMovies(){
        List<String> movies = new ArrayList<>();
        for (String movieName : movieHashMap.keySet()){
            movies.add(movieName);
        }
        return movies;
    }
    public String deleteDirectorByName(String directorName){
        directorHashMap.remove(directorName);
        if (pairHashMap.containsKey(directorHashMap.get(directorName))){
            List<Movie> movies = pairHashMap.get(directorHashMap.get(directorName));
            for (Movie movie : movies){
                if (movieHashMap.containsKey(movie)){
                    movieHashMap.remove(movie);
                }
            }
            pairHashMap.remove(directorHashMap.get(directorName));
        }
        return "Removed successfully";
    }
    public String deleteAllDirectors(){
        for (Director director : pairHashMap.keySet()) {
            List<Movie> movies = pairHashMap.get(directorHashMap.get(director));
            for (Movie movie : movies) {
                if (movieHashMap.containsKey(movie.getName())) {
                    movieHashMap.remove(movie.getName());
                }
            }
        }
        directorHashMap.clear();
        pairHashMap.clear();
        return "All details deleted successfully";
    }
}
