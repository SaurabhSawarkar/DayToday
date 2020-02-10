package com.example.daytodaytest.dashboard

import android.os.Bundle
import com.example.daytodaytest.R
import com.example.daytodaytest.base.BaseActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity to show the list of movies.
 */
class DashboardActivity : BaseActivity() {
    private val viewModel by viewModel<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        tv_ss.setOnClickListener { viewModel.fetchListOfMovies() }
    }
}