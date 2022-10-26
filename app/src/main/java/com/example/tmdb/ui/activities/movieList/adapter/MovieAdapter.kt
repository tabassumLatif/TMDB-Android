package com.example.tmdb.ui.activities.movieList.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.databinding.MovieItemBinding
import com.example.tmdb.domain.model.Movie
import javax.inject.Inject

class MovieAdapter @Inject constructor():
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    lateinit var loadMore: () -> Unit
    lateinit var onItemClick: (Int) -> Unit
    lateinit var onFavoriteClick: (Movie?) -> Unit
    var isLoadMoreEnable = true
    private var movies: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: MovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.movie_item, parent, false
        )

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.onBind(movie)

        if (position + 5 == itemCount && isLoadMoreEnable) {
            loadMore.invoke()
        }
    }

    override fun getItemCount(): Int {
        return if (movies.isEmpty()) {
            0
        } else {
            movies.size
        }
    }

    fun addNewData(newMovieList: ArrayList<Movie>) {
        movies.addAll(newMovieList)
        notifyDataSetChanged()
    }

    fun clearData() {
        val movieListSize: Int = movies.size
        movies.clear()
        notifyItemRangeRemoved(0, movieListSize)

    }

    inner class MovieViewHolder(private val movieItemBinding: MovieItemBinding) :
        RecyclerView.ViewHolder(movieItemBinding.root) {

        fun onBind(movie: Movie) {
            movieItemBinding.movie = movie
            movieItemBinding.favoriteImg.setImageDrawable(
                ContextCompat.getDrawable(
                    movieItemBinding.favoriteImg.context, if (movie.isFavorite) {
                        R.drawable.ic_favorite_filled
                    } else {
                        R.drawable.ic_favorite
                    }
                )
            )


            movieItemBinding.root.setOnClickListener {
                if (this@MovieAdapter::onItemClick.isInitialized) {
                    onItemClick.invoke(movie.id)
                }
            }

            movieItemBinding.favoriteImg.setOnClickListener {
                if (this@MovieAdapter::onFavoriteClick.isInitialized) {

                    if (movie.isFavorite.not()) {
                        movie.isFavorite = true
                        movies[adapterPosition].isFavorite = true
                        movieItemBinding.favoriteImg.setImageDrawable(
                            ContextCompat.getDrawable(
                                movieItemBinding.favoriteImg.context,
                                R.drawable.ic_favorite_filled
                            )
                        )
                        onFavoriteClick.invoke(movie)
                    } else {
                        onFavoriteClick.invoke(null)
                    }

                }

            }
        }

    }
}