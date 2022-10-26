package com.example.tmdb.ui.activities.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: DetailViewModel
    private val movieIdConst = "movie_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_detail
        )
        setUpToolBar()

        val movieId: Int = intent.extras?.getInt(movieIdConst) ?: 0
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        addErrorObserver()
        viewModel.getMovieDetail(movieId)

    }

    private fun addErrorObserver() {
        viewModel.errorViewStateData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.toolbarLayout.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}