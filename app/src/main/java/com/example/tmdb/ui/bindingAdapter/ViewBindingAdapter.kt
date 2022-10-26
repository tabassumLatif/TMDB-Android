package com.example.tmdb.ui.bindingAdapter

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.BuildConfig.IMAGE_BASE_URL
import com.example.tmdb.domain.model.Movie
import com.example.tmdb.domain.model.MovieResponse
import com.example.tmdb.ui.activities.movieList.adapter.MovieAdapter
import java.util.*

object ViewBindingAdapter {

    @BindingAdapter("doubleText")
    @JvmStatic
    fun setText(view: TextView, double: Double) {
        view.text = double.toString()
    }

    @BindingAdapter("imageResource")
    @JvmStatic
    fun setImage(imageView: ImageView, image: String?) {
        if (image != null) {
            Glide.with(imageView.context)
                .load(IMAGE_BASE_URL + image)
                .into(imageView)
        }
    }

    @BindingAdapter("yearDate")
    @JvmStatic
    fun setYear(textView: TextView, date: String) {
        val releaseYear = setReleaseDate(date)
        if (releaseYear != null) {
            textView.text = releaseYear

        } else {
            textView.visibility = View.GONE
        }
    }

    private fun setReleaseDate(releaseDate: String): Spannable? {
        if (releaseDate.isNotEmpty()) {
            val releaseYear: Int = releaseDate.split("-")[0].toInt()
            val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)

            val releaseYearFormat = SpannableString("($releaseYear)")

            if (releaseYear == currentYear) {

                releaseYearFormat.setSpan(
                    StyleSpan(Typeface.BOLD),
                    1,
                    releaseYearFormat.length - 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                releaseYearFormat.setSpan(
                    ForegroundColorSpan(Color.RED),
                    1,
                    releaseYearFormat.length - 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                return releaseYearFormat


            } else {
                return releaseYearFormat
            }
        } else {
            return null
        }
    }

    @BindingAdapter("setMoviesAdapter")
    @JvmStatic
    fun setMoviesAdapter(rvMovies: RecyclerView, movieResponse: MovieResponse?) {
        if (movieResponse == null) {
            return
        }
        val adapter = rvMovies.adapter as MovieAdapter
        if (movieResponse.results.isEmpty() && adapter.itemCount == 0) {
            adapter.clearData()
        } else {
            if (movieResponse.results.isEmpty()) {
                adapter.isLoadMoreEnable = false
            }
            adapter.addNewData(movieResponse.results)
        }
    }

    @BindingAdapter("setFavoriteMoviesAdapter")
    @JvmStatic
    fun setFavoriteMoviesAdapter(rvMovies: RecyclerView, movies: List<Movie>) {
        if (movies == null) {
            return
        }
        val adapter = rvMovies.adapter as MovieAdapter
        adapter.isLoadMoreEnable = false
        adapter.addNewData(movies as ArrayList<Movie>)
    }

}