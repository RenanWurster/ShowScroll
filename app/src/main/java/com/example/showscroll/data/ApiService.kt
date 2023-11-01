package com.example.showscroll.data

import com.example.retrofit.seriesdetail.domain.Seasons
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
  @GET("/shows")
  suspend fun getSeries(): List<Series>

  @GET("/shows/{id}/seasons")
  suspend fun getSeasons(@Path("id") id : Int): List<Seasons>

  @GET("/search/shows")
  suspend fun searchShows(@Query("q") query : String): List<SearchSeries>
}
