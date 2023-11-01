package com.example.showscroll.features.showdetail.data

import com.example.retrofit.seriesdetail.domain.Seasons
import com.example.showscroll.data.ApiService
import javax.inject.Inject

class SeriesDetailRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getSeasons(id : Int): List<Seasons> {
        return apiService.getSeasons(id)
    }
}