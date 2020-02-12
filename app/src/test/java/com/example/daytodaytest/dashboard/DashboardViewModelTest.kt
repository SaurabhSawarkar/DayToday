package com.example.daytodaytest.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.daytodaytest.dashboard.model.MoviesResponse
import com.example.daytodaytest.dashboard.model.Results
import com.example.daytodaytest.dashboard.rule.RxSingleThreadRule
import com.example.daytodaytest.dayTodayModule
import io.reactivex.Observable
import org.junit.*
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito

class DashboardViewModelTest : KoinTest {

    @get:Rule
    var testSchedulerRule = RxSingleThreadRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    val mockRepo: DashboardRepo by inject()

    @Before
    fun before() {
        startKoin { modules(listOf(dayTodayModule)) }
        declareMock<DashboardRepo> { }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testGetListOfMoviesPositive() {
        val mockResponse = Mockito.mock(MoviesResponse::class.java)

        val list = arrayListOf<Results>()
        list.add(Results())
        list.add(Results())
        list.add(Results())
        list.add(Results())
        Mockito.`when`(mockResponse.results).thenReturn(list)
        Mockito.`when`(mockRepo.getListOfMovies(anyInt(), anyInt()))
            .thenReturn(Observable.just(mockResponse))

        val dashboardVM = DashboardViewModel(mockRepo)
        dashboardVM.fetchListOfMovies()

        Assert.assertNotNull(dashboardVM.response.value)
        Assert.assertEquals(dashboardVM.response.value?.size, 4)
    }

    @Test
    fun testGetListOfMoviesNegative() {
        //Expected return type is Observable of type MoviesResponse, thus Observable.just(Throwable()) generates the error
        Mockito.`when`(mockRepo.getListOfMovies(anyInt(), anyInt()))
            .thenAnswer {
                Observable.just(Throwable())
            }

        val dashboardVM = DashboardViewModel(mockRepo)
        dashboardVM.fetchListOfMovies()
        Assert.assertNotNull(dashboardVM.error.value)
    }
}