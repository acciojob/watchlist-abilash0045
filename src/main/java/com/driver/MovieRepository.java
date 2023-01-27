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
    HashMap<String,List<String>> pairHashMap = new HashMap<>();
    public String  addMovie(Movie movie){
        movieHashMap.put(movie.getName(),movie);
        return "Movie added successfully";
    }
    public String addDirector(Director director){
        directorHashMap.put(director.getName(),director);
        return "Director added successfully";
    }
    public String addMovieDirectorPair(String movieName,String directorName){
        if (pairHashMap.containsKey(directorName)){
            List<String> movie = pairHashMap.get(directorName);
            movie.add(movieName);
            pairHashMap.put(directorName,movie);
        }else{
            List<String> list = new ArrayList<>();
            list.add(movieName);
            pairHashMap.put(directorName,list);
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
        return pairHashMap.get(directorName);
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
        if (pairHashMap.containsKey(directorName)){
            List<String> movieNames = pairHashMap.get(directorName);
            for (String movieName : movieNames){
                if (movieHashMap.containsKey(movieName)){
                    movieHashMap.remove(movieName);
                }
            }
            pairHashMap.remove(directorName);
        }
        return "Removed successfully";
    }
    public String deleteAllDirectors(){
        for (String directorName : pairHashMap.keySet()) {
            List<String> movieNames = pairHashMap.get(directorName);
            for (String movieName : movieNames) {
                if (movieHashMap.containsKey(movieName)) {
                    movieHashMap.remove(movieName);
                }
            }
        }
        directorHashMap.clear();
        pairHashMap.clear();
        return "All details deleted successfully";
    }
}
