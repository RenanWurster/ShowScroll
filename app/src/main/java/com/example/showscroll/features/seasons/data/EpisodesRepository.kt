package com.example.showscroll.features.seasons.data
import com.example.showscroll.data.ApiService
import com.example.showscroll.data.Episodes
import javax.inject.Inject

class EpisodesRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getEpisodes(id : Int): List<Episodes> {
        return apiService.getEpisodes(id)
    }
}