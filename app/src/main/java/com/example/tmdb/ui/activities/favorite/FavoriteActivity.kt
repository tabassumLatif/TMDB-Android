package com.example.tmdb.ui.activities.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityFavoriteBinding
import com.example.tmdb.ui.activities.detail.DetailActivity
import com.example.tmdb.ui.activities.movieList.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    @Inject
    lateinit var movieAdapter: MovieAdapter
    private val movieIdConst = "movie_id"
    lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite)
        setUpToolBar()
        initializeRecyclerView()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        addMovieItemClickListener()
    }

    private fun addMovieItemClickListener() {
        movieAdapter.onItemClick = { movieId ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(movieIdConst, movieId)
            startActivity(intent)
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.toolbarLayout.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Favorites"
    }

    private fun initializeRecyclerView() {
        binding.rvFavoriteMovies.adapter = movieAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}