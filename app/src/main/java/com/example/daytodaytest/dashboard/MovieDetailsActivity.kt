package com.example.daytodaytest.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.daytodaytest.R
import com.example.daytodaytest.base.BaseActivity
import com.example.daytodaytest.dashboard.model.Results
import com.example.daytodaytest.extension.loadUrl
import com.example.daytodaytest.extension.spannableBold
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class MovieDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_KEY_MOVIE = "intent_key_movie"
        fun getIntent(context: Context, movie: Results): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(INTENT_KEY_MOVIE, movie)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setMovieDetails()
    }

    private fun setMovieDetails() {
        val picasso: Picasso by inject(named("AUTH_PICASSO"))

        val movie = intent.getParcelableExtra<Results>(INTENT_KEY_MOVIE)
        movie?.let {
            img_details.loadUrl(picasso, "https://image.tmdb.org/t/p/w500${movie.poster_path}")
            tv_duration.spannableBold("Duration: ", "3 hours 29 min")
            tv_release_date.spannableBold("Release Date: ", movie.release_date)
            tv_language.spannableBold("Language: ", movie.original_language)
            tv_genres.spannableBold("Genres: ", "Crime, History, Drama")
            tv_rating.spannableBold("Ratings: ", movie.vote_average)
            tv_about.spannableBold("Ratings: ", movie.overview)
            tv_cast.spannableBold(
                "Ratings: ",
                "Paul Walker \n Vin Diesel \n Michelle Rodriguez \n Jordana Brewster \n Rick Yune \n Paul Walker \n" +
                        " Vin Diesel \n" +
                        " Michelle Rodriguez \n" +
                        " Jordana Brewster \n" +
                        " Rick Yune"
            )
        }
    }
}