package com.example.retrofit.data.di
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

  @Provides
  @Singleton
  fun providesOkhttpClient() : OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(10, TimeUnit.SECONDS)
      .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      })
      .build()
  }

  @Provides
  @Singleton
  fun providesRetrofit(client: OkHttpClient) : Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://api.tvmaze.com")
      .client(client)
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
      .build()
  }
}