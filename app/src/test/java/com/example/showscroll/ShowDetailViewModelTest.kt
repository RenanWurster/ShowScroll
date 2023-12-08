package com.example.showscroll

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.showscroll.data.ImageSeasons
import com.example.showscroll.data.Seasons
import com.example.showscroll.features.showdetail.data.SeriesDetailRepository
import com.example.showscroll.features.showdetail.presentation.ShowDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ShowDetailViewModelTest {

  // Set the main coroutines dispatcher for unit testing
  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  // Executes each task synchronously using Architecture Components
  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: ShowDetailViewModel
  private lateinit var detailRepository: SeriesDetailRepository

  @Before
  fun setup() {
    // Mock your dependencies
    detailRepository = mockk()
    viewModel = ShowDetailViewModel(detailRepository)
  }

  @Test
  fun `test getSeasonsById`() {
    // Given
    val expectedSeasons = listOf(
      Seasons(
        id = 1,
        number = 1,
        summary = "Summary for Season 1",
        episodeOrder = 10,
        premiereDate = "2023-01-01",
        endDate = "2023-03-31",
        image = ImageSeasons
          (
          medium = "url_to_medium_image_season_1",
          original = "url_to_original_image_season_1"
        )
      ),
      Seasons(
        id = 2,
        number = 2,
        summary = "Summary for Season 2",
        episodeOrder = 12,
        premiereDate = "2023-04-01",
        endDate = "2023-06-30",
        image = ImageSeasons(
          medium = "url_to_medium_image_season_2",
          original = "url_to_original_image_season_2"
        )
      )
      // Add more Seasons objects as needed
    )
    val id = 1 // Sample ID

    // Mock the behavior of your repository method
    coEvery { detailRepository.getSeasons(id) } returns expectedSeasons

    // When
    viewModel.getSeasonsById(id)

    // Then
    assertEquals(expectedSeasons, viewModel.seasons.value)
  }
}