package com.example.showscroll.features.seasons.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.showscroll.data.Episodes
import com.example.showscroll.data.Seasons
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.example.showscroll.databinding.ActivitySeasonsBinding
import com.example.showscroll.features.episodedetail.presentation.EpisodesDetail
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class SeasonActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeasonsBinding
    private lateinit var seasons: Seasons
    private val seasonViewModel: SeasonViewModel by viewModels()
    private  var episodesAdapter = EpisodesAdapter(::episodesClickListener)

    private fun episodesClickListener(episodes: Episodes) {
        startActivity(EpisodesDetail.createIntent2(this, episodes))
    }

    companion object {
        private const val SEASONS_KEY = "seasons_key"

        fun createIntent1(context: Context, seasons: Seasons): Intent {
            val intent = Intent(context, SeasonActivity::class.java)
            intent.putExtra(SEASONS_KEY, seasons)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeasonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seasons = intent.extras?.get(SEASONS_KEY) as Seasons
        binding.rvEpisodes.adapter = episodesAdapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        "Season: ${seasons.number}".also { binding.txtSeasonNumber.text = it }
        "Episodes: ${seasons.episodeOrder}".also { binding.txtPremiereDate.text = it }

        val premiereDateFormatted = seasons.premiereDate?.let { formatDate(it) }
        val endDateFormatted = seasons.endDate?.let { formatDate(it) }

        val formattedText = "Premiered: $premiereDateFormatted   Ended: $endDateFormatted"
        binding.txtEndDate.text = formattedText


        binding.imageSeason.load(seasons.image?.original)
        {
            crossfade(true)
            transformations(RoundedCornersTransformation(15f))
        }

        seasons.id?.let { seasonViewModel.getEpisodesById(it) }
        seasonViewModel.episodes.observe(this, Observer {
                episodes -> episodes?.let {
            episodesAdapter.submitList(it)
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

    private fun formatDate(dateString: String): String? {
        val formatterFromAPI = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val desiredFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val date = formatterFromAPI.parse(dateString)
        return date?.let { desiredFormat.format(it) }
    }
}