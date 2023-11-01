package com.example.showscroll.features.showdetail.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.retrofit.seriesdetail.domain.Seasons
import com.example.showscroll.data.Series
import com.example.showscroll.databinding.ActivitySeriesDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowDetailActivity : AppCompatActivity() {
    private lateinit var series: Series
    private lateinit var binding: ActivitySeriesDetailBinding
    private val showDetailViewModel: ShowDetailViewModel by viewModels()
    private val seasonsAdapter = ShowDetailAdapter(::seasonsClickListener)


    private fun seasonsClickListener(seasons: Seasons) {
        //startActivity(SeasonActivity.createIntent1(this,seasons))
    }

    companion object {
        private const val SERIES_KEY = "series_key"

        fun createIntent(context: Context, series: Series): Intent {
            val intent = Intent(context, ShowDetailActivity::class.java)
            intent.putExtra(SERIES_KEY, series)
            return intent

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeriesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSeasons.adapter = seasonsAdapter

        series = intent.extras?.get(SERIES_KEY) as Series

        binding.imageFilmDetail.load(series.image?.original)
        {
            crossfade(true)
            transformations(RoundedCornersTransformation(15f))
        }
        binding.txtfilmDetail.text = series.summary
        binding.txtNameFilmDetail.text = series.name
        binding.txtGenresFilmDetail.text = series.genres.toString()

        series.id?.let { showDetailViewModel.getSeasonsById(it) }

        showDetailViewModel.seasons.observe(this, Observer {
                seasons -> seasons?.let {
            Log.d("renan","chegando$it")
            seasonsAdapter.submitList(it)
        }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}