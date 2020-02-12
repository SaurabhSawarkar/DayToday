package com.example.daytodaytest.dashboard

import com.example.daytodaytest.dashboard.model.MoviesResponse
import com.example.daytodaytest.dashboard.model.Results
import io.reactivex.Observable

/**
 * Dashboard repo interface. Provides the interface to fetch data from server and/or local DB
 */
interface DashboardRepo {

    /**
     * Fetch the list of movies from the server
     *
     * @param listId - listId
     * @param page - page
     * @return - Observable of type MoviesResponse
     */
    fun getListOfMovies(listId: Int, page: Int): Observable<MoviesResponse>

    /**
     * Saves the movies on the local databse
     *
     * @param moviesResponse - response of getListOfMovies()
     * @return - Observable of type MoviesResponse
     */
    fun saveMoviesLocally(moviesResponse: MoviesResponse): Observable<MoviesResponse>


    /**
     * Fetches the list of movies from the local db
     *
     * @return - Observable of list of Results
     */
    fun getMoviesLocally(): Observable<List<Results>>
}