package com.example.daytodaytest.dashboard

import androidx.lifecycle.MutableLiveData
import com.example.daytodaytest.base.BaseViewModel
import com.example.daytodaytest.dashboard.model.MoviesResponse
import com.example.daytodaytest.network.AppRxSchedulers

/**
 * View model provides data required by the UI.
 *
 * @param dashboardRepo - repo to fetch data for UI
 */
class DashboardViewModel(private val dashboardRepo: DashboardRepo) : BaseViewModel() {
    val response = MutableLiveData<MoviesResponse>()
    val error = MutableLiveData<Throwable>()

    fun fetchListOfMovies() {
        val disposable =
            dashboardRepo.getListOfMovies(4, 1).observeOn(AppRxSchedulers.mainThread())
                .subscribe({
                    response.postValue(it)
                }, {
                    error.postValue(it)
                })

        addDisposable(disposable)
    }
}