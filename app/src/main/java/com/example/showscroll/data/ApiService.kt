package com.example.showscroll.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
  @GET("/shows")
  suspend fun getSeries(): List<Series>

  @GET("/search/shows")
  suspend fun searchShows(@Query("q") query : String): List<SearchSeries>
}
