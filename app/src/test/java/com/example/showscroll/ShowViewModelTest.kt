import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.showscroll.data.ApiService
import com.example.showscroll.data.ImageSerie
import com.example.showscroll.data.Rating
import com.example.showscroll.data.SearchSeries
import com.example.showscroll.data.Series
import com.example.showscroll.features.home.data.SeriesRepository
import com.example.showscroll.features.home.presentation.ShowViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class ShowViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: ShowViewModel
  private lateinit var seriesRepository: SeriesRepository
  private val testDispatcher = TestCoroutineDispatcher()
  private lateinit var searchSeries: SearchSeries

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    seriesRepository = mockk()
    viewModel = ShowViewModel(seriesRepository)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
    testDispatcher.cleanupTestCoroutines()
  }

  @Test
  fun `test if series are loaded on init`() = runBlockingTest {
    // Given
    val seriesList = listOf(
      Series(
        genres = listOf("Action", "Drama"),
        id = 1,
        image = ImageSerie(medium = "url_to_medium_image", original = "url_to_original_image"),
        name = "Series 1",
        summary = "Summary of the series",
        premiered = "2023-01-01",
        ended = "2023-12-31",
        type = "TV",
        rating = Rating(average = 9.0)
      )
    )

    val apiService = mockk<ApiService> {
      coEvery { getSeries() } returns seriesList
    }

    // Configurando o SeriesRepository com o ApiService mockado
    val seriesRepository = SeriesRepository(apiService)

    // Chamada ao método dentro de um contexto de teste de coroutine
    val result = seriesRepository.getSeries()

    // Verificar se o resultado é o esperado
    assertEquals(seriesList, result)
  }

  @Test
  fun `test search function with non-empty query`() {
    // Given
    val query = "Series 1"
    val searchResults = listOf(
      SearchSeries(
        show = Series(
          genres = listOf("Action", "Drama"),
          id = 1,
          image = ImageSerie(medium = "url_to_medium_image", original = "url_to_original_image"),
          name = "Series 1",
          summary = "Summary of the series",
          premiered = "2023-01-01",
          ended = "2023-12-31",
          type = "TV",
          rating = Rating(average = 9.0)
        )
      )
    )

    val apiService = mockk<ApiService> {
      coEvery { searchShows(query) } returns searchResults
    }

    val seriesRepository = SeriesRepository(apiService)
    val viewModel = ShowViewModel(seriesRepository)

    // When
    viewModel.onSearch(query)

    // Then
    val latch = CountDownLatch(1)

    viewModel.series.observeForever { series ->
      assertEquals(searchResults.map { it.show }, series)
      latch.countDown()
    }

    latch.await(2, TimeUnit.SECONDS)
  }

  @Test
  fun `test search function with empty query`() {
    // Given
    val query = ""
    val searchResults = listOf(
      Series(
        genres = listOf("Action", "Drama"),
        id = 1,
        image = ImageSerie(medium = "url_to_medium_image", original = "url_to_original_image"),
        name = "Series 1",
        summary = "Summary of the series",
        premiered = "2023-01-01",
        ended = "2023-12-31",
        type = "TV",
        rating = Rating(average = 9.0)
      )
    )
    val liveData = MutableLiveData<List<Series>>()
    liveData.value = searchResults

    // When
    viewModel.onSearch(query)

    // Then
    val latch = CountDownLatch(1)

    viewModel.series.observeForever { series ->
      assertEquals(searchResults, series)
      latch.countDown()
    }

    latch.await(2, TimeUnit.SECONDS)
  }
}