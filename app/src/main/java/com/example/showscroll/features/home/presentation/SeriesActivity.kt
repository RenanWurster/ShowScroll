package com.example.showscroll.features.home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import com.example.showscroll.R
import com.example.showscroll.data.Series
import com.example.showscroll.databinding.ActivitySeriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySeriesBinding
  private val seriesViewModel: SeriesViewModel by viewModels()
  private val adapterSeries = SeriesAdapter(::seriesClickListener)

  private fun seriesClickListener(series: Series) {
    //startActivity(SeriesDetail.createIntent(this, series))
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySeriesBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.rvSeries.adapter = adapterSeries

    seriesViewModel.series.observe(this) { series ->
      series?.let {
        Log.d("renan", "chegando$it")
        adapterSeries.submitList(it)
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    val searchItem = menu.findItem(R.id.menu_search)
    if (searchItem != null) {
      val searchView = searchItem.actionView as android.widget.SearchView
      searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
          seriesViewModel.onSearch(newText)
          return true
        }
      })
    }
    return super.onCreateOptionsMenu(menu)
  }
}