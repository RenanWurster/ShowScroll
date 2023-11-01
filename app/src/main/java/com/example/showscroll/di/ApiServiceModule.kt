package com.example.showscroll.di

import com.example.showscroll.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ApiServiceModule {
  @Provides
  fun providesApiServices(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)
}