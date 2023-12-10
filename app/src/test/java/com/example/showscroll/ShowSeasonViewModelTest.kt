package com.example.showscroll

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.showscroll.data.Episodes
import com.example.showscroll.data.ImageEpisodes
import com.example.showscroll.features.seasons.data.EpisodesRepository
import com.example.showscroll.features.seasons.presentation.SeasonViewModel
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
class ShowSeasonViewModelTest {

  // Set the main coroutines dispatcher for unit testing
  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  // Executes each task synchronously using Architecture Components
  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: SeasonViewModel
  private lateinit var episodesRepository: EpisodesRepository

  @Before
  fun setup() {
    // Mock your dependencies
    episodesRepository = mockk()
    viewModel = SeasonViewModel(episodesRepository)
  }

  @Test
  fun `test getSeasonsById`() {
    // Given
    val expectedEpisodes = listOf(
      Episodes(
        id = 1,
        name = "Pilot",
        season = 1,
        number = 1,
        image = ImageEpisodes
          (
          medium = "url_to_medium_image_episode_1",
          original = "url_to_original_image_episode_1"
        ),
        summary = "blablabblabla",
        runtime = 2
      ),
    )
    val id = 1 // Sample ID

    // Mock the behavior of your repository method
    coEvery { episodesRepository.getEpisodes(id) } returns expectedEpisodes

    // When
    viewModel.getEpisodesById(id)

    // Then
    assertEquals(expectedEpisodes, viewModel.episodes.value)
  }
}