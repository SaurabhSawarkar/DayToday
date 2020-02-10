package com.example.daytodaytest.dashboard

import com.example.daytodaytest.base.BaseRepoImpl
import com.example.daytodaytest.dashboard.database.DashboardDatabaseManager
import com.example.daytodaytest.dashboard.model.MoviesResponse
import com.example.daytodaytest.dashboard.model.Results
import com.example.daytodaytest.network.AppRxSchedulers
import io.reactivex.Observable

/**
 * Dashboard repo to fetch data for view model
 *
 * @param api - api to fetch data from server
 * @param databaseManager - database manager to fetch data from local DB.
 */
class DashboardRepoImpl(
    private val api: DashboardAPI,
    private val databaseManager: DashboardDatabaseManager
) : BaseRepoImpl(), DashboardRepo {

    override fun getListOfMovies(listId: Int, page: Int): Observable<MoviesResponse> {
        return isInternetAvailable().toObservable().concatMap { isInternetAvailable ->
            if (isInternetAvailable) {
                api.getListOfMovies(listId, page)
                    .subscribeOn(AppRxSchedulers.network()).concatMap {
                        saveMoviesLocally(it)
                    }
            } else {
                getMoviesLocally().concatMap { resultsList ->
                    return@concatMap if (resultsList.isNullOrEmpty()) {
                        getNetworkNotAvailable(type = MoviesResponse::class.java) as Observable<MoviesResponse>
                    } else {
                        Observable.just(MoviesResponse(results = resultsList))
                    }
                }

            }
        }
    }

    override fun saveMoviesLocally(moviesResponse: MoviesResponse): Observable<MoviesResponse> {
        return Observable.create<MoviesResponse> { emitter ->
            databaseManager.insertOrUpdateResults(moviesResponse.results ?: arrayListOf())
            emitter.onNext(moviesResponse)
            emitter.onComplete()
        }.subscribeOn(AppRxSchedulers.database())
    }

    override fun getMoviesLocally(): Observable<List<Results>> {
        return Observable.create<List<Results>> { emitter ->
            emitter.onComplete()
        }.subscribeOn(AppRxSchedulers.database())
    }
}