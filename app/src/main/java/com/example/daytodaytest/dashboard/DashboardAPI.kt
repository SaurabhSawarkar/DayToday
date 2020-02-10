package com.example.daytodaytest.dashboard

import com.example.daytodaytest.dashboard.model.MoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DashboardAPI {

    @GET("list/{list_id}")
    fun getListOfMovies(@Path("list_id") listId: Int, @Query("page") page: Int)
            : Observable<MoviesResponse>
}