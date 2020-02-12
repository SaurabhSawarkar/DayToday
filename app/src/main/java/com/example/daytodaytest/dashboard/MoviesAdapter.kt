package com.example.daytodaytest.dashboard


import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.daytodaytest.BuildConfig
import com.example.daytodaytest.R
import com.example.daytodaytest.dashboard.model.Results
import com.example.daytodaytest.extension.loadUrl
import com.squareup.picasso.Picasso


class MoviesAdapter(
    private val moviesList: List<Results>?,
    private val picasso: Picasso,
    private val listener: MovieItemClickListener
) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    interface MovieItemClickListener {
        fun onMovieClicked(movie: Results)
    }

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var tvMoviewTitle: TextView = itemView.findViewById(R.id.tv_movie_title)
        internal var tvMovieRating: TextView = itemView.findViewById(R.id.tv_movie_rating)
        internal var imgMoviePoster: ImageView = itemView.findViewById(R.id.img_movie_poster)
        internal var parent: CardView = itemView.findViewById(R.id.card_parent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_recycler, parent, false)

        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = moviesList?.get(position)
        holder.tvMoviewTitle.text = movie?.title
        holder.tvMovieRating.text = movie?.vote_average
        holder.parent.tag = movie
        holder.parent.setOnClickListener {
            listener.onMovieClicked(it.tag as Results)
        }

        moviesList?.get(position)?.poster_path?.let {
            holder.imgMoviePoster.loadUrl(picasso, "https://image.tmdb.org/t/p/w500$it")
        }

    }

    override fun getItemCount(): Int {
        return moviesList?.size ?: 0
    }
}