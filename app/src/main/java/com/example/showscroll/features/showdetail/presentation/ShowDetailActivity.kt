package com.example.showscroll.features.showdetail.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.showscroll.data.Seasons
import com.example.showscroll.data.Series
import com.example.showscroll.databinding.ActivitySeriesDetailBinding
import com.example.showscroll.features.seasons.presentation.SeasonActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class ShowDetailActivity : AppCompatActivity() {
    private lateinit var series: Series
    private lateinit var binding: ActivitySeriesDetailBinding
    private val showDetailViewModel: ShowDetailViewModel by viewModels()
    private val seasonsAdapter = ShowDetailAdapter(::seasonsClickListener)

    private fun seasonsClickListener(seasons: Seasons) {
        startActivity(SeasonActivity.createIntent1(this, seasons))
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

        val formattedSummary = removeHtmlTags(series.summary ?: "")
        binding.txtfilmDetail.text = formattedSummary

        binding.txtNameFilmDetail.text = series.name

        binding.txtRating.text = series.rating.average.toString()

        val formattedGenres = series.genres.joinToString(", ")
        binding.txtGenresFilmDetail.text = formattedGenres

        val premieredDate = series.premiered?.let { formatDate(it) }
        val endedDate = series.ended?.let { formatDate(it) }

        val formattedText = "Premiered: $premieredDate  Ended: $endedDate"
        binding.txtDateAndSeasonNumber.text = formattedText

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

    private fun removeHtmlTags(htmlText: String): Spanned {
        return Html.fromHtml(htmlText, null, null)
    }

    private fun formatDate(dateString: String): String? {
        val formatterFromAPI = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val desiredFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val date = formatterFromAPI.parse(dateString)
        return date?.let { desiredFormat.format(it) }
    }
}