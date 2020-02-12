package com.example.daytodaytest.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daytodaytest.R
import com.example.daytodaytest.base.BaseActivity
import com.example.daytodaytest.dashboard.model.Results
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

/**
 * Activity to show the list of movies.
 */
class MovieListActivity : BaseActivity() {
    private val viewModel by viewModel<DashboardViewModel>()
    private val picasso: Picasso by inject(named("AUTH_PICASSO"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        initViews()
        initObservers()
        viewModel.fetchListOfMovies()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        rc_movies.layoutManager = LinearLayoutManager(this)
    }

    private fun initObservers() {
        viewModel.response.observe(this, Observer {
            val adapter = MoviesAdapter(it, picasso, movieItemClickListener)
            rc_movies.adapter = adapter
            rc_movies.visibility = View.VISIBLE
            progress_bar.visibility = View.GONE
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(
                this,
                "Error occurred during network call ${it.message}",
                Toast.LENGTH_SHORT
            ).show()
            progress_bar.visibility = View.GONE
        })
    }

    private val movieItemClickListener = object : MoviesAdapter.MovieItemClickListener {
        override fun onMovieClicked(movie: Results) {
            startActivity(MovieDetailsActivity.getIntent(this@MovieListActivity, movie))
        }
    }
}