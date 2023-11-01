package com.example.showscroll.features.home.data

import com.example.showscroll.data.ApiService
import com.example.showscroll.data.SearchSeries
import com.example.showscroll.data.Series
import javax.inject.Inject

class SeriesRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getSeries(): List<Series> {
        return apiService.getSeries()
    }

    suspend fun searchShows(name: String): List<SearchSeries> {
        return apiService.searchShows(name)
    }


}