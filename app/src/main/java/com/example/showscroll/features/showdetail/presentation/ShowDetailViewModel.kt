package com.example.showscroll.features.showdetail.presentation

import androidx.lifecycle.*
import com.example.showscroll.features.showdetail.data.SeriesDetailRepository
import com.example.showscroll.data.Seasons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(private val detailRepository: SeriesDetailRepository): ViewModel() {

    private val _seasons = MutableLiveData<List<Seasons>>()
    val seasons : LiveData<List<Seasons>> = _seasons

    fun getSeasonsById(id: Int){
        viewModelScope.launch {
            _seasons.value = detailRepository.getSeasons(id)
        }
    }
}