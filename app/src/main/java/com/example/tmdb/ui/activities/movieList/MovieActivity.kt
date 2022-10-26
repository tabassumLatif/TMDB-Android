package com.example.tmdb.ui.activities.movieList

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityMovieBinding
import com.example.tmdb.ui.activities.detail.DetailActivity
import com.example.tmdb.ui.activities.favorite.FavoriteActivity
import com.example.tmdb.ui.activities.movieList.adapter.MovieAdapter
import com.example.tmdb.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    @Inject
    lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel
    private val queryHint = "Search Movie by Name"
    private val movieIdConst = "movie_id"
    private var query: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_movie
        )
        setSupportActionBar(binding.toolbarLayout.materialToolbar)
        initializeRecyclerViewAdapter()
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.isLoading.postValue(true)
        viewModel.getMovies()

    }

    private fun initializeRecyclerViewAdapter() {
        binding.rvMovies.adapter = movieAdapter

        onMovieClickListener()
        addLoadMoreListener()
        onFavoriteClickListener()
    }

    private fun addLoadMoreListener() {
        movieAdapter.loadMore = {
            if (NetworkUtils.isNetworkConnected(this)) {
                if (query.isEmpty()) {
                    viewModel.loadMoreMovies()
                } else {
                    viewModel.loadMoreWithSearch(query)
                }
            }
        }
    }

    private fun onMovieClickListener() {
        movieAdapter.onItemClick = { movieId ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(movieIdConst, movieId)
            startActivity(intent)
        }
    }

    private fun onFavoriteClickListener() {
        movieAdapter.onFavoriteClick = {
            if (it != null) {
                viewModel.addToFavorite(it)
            } else {
                Toast.makeText(this, "This movie has already added in favorite", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.search_menu)
        val searchView: SearchView = searchItem?.actionView as SearchView

        setUpSearchViewUI(searchView)
        searchQueryListener(searchView)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_menu -> {
                moveToFavoriteActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveToFavoriteActivity() {
        intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
    }

    private fun searchQueryListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                movieAdapter.clearData()
                query = newText ?: ""
                viewModel.apply {
                    page = 1
                    isLoading.postValue(true)
                    isResultFound.postValue(true)
                    getSearchMovieResult(query)
                }
                return true
            }

        })
    }

    private fun setUpSearchViewUI(searchView: SearchView) {
        searchView.queryHint = queryHint
        searchView.maxWidth = Int.MAX_VALUE
        val text: TextView = searchView.findViewById(androidx.appcompat.R.id.search_src_text)

        ContextCompat.getColor(this, R.color.white).let {
            text.setTextColor(it)
            text.setHintTextColor(it)
        }

        val searchPlate: View = searchView.findViewById(androidx.appcompat.R.id.search_plate)
        searchPlate.setBackgroundColor(resources.getColor(R.color.purple_500, null))

    }

}

