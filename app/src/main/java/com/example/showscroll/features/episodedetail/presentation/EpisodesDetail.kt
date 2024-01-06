package com.example.showscroll.features.episodedetail.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.MenuItem
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.showscroll.R
import com.example.showscroll.data.Episodes
import com.example.showscroll.databinding.ActivityEpisodesDetailBinding
import com.example.showscroll.features.seasons.presentation.SeasonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesDetail : AppCompatActivity() {

    private val seasonViewModel: SeasonViewModel by viewModels()
    private lateinit var episodes: Episodes
    private lateinit var binding: ActivityEpisodesDetailBinding

    companion object {
        private const val EPISODES_KEY = "episodes_key"

        fun createIntent2(context: Context, episodes: Episodes): Intent {
            val intent = Intent(context, EpisodesDetail::class.java)
            intent.putExtra(EPISODES_KEY, episodes)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        episodes = intent.extras?.get(EPISODES_KEY) as Episodes

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setSupportActionBar(findViewById(R.id.toolbarEpisode))

        findViewById<ImageButton>(R.id.btnBackEpisode).setOnClickListener {
            onBackPressed()
        }

        binding.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)

            intent.putExtra(Intent.EXTRA_TEXT, "i have a nice episode for you: ${episodes.name}")

            val shareIntent = Intent.createChooser(intent, null)

            intent.type = "text/plain"

            startActivity(shareIntent)
        }

        binding.imageEpisodeDetail.load(episodes.image?.original)

        val formattedSummary = removeHtmlTags(episodes.summary ?: "")
        binding.txtSummaryEpisodeDetail.text = formattedSummary

        binding.txtRunTimeEpisode.text = episodes.runtime.toString()
        "  ${episodes.runtime} Min".also { binding.txtRunTimeEpisode.text = it }
        binding.txtNameEpisodeDetail.text = episodes.name
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
}