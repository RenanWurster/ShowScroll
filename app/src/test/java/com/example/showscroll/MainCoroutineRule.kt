import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher() {

  override fun starting(description: Description?) {
    super.starting(description)
    setupCoroutines()
  }

  override fun finished(description: Description?) {
    super.finished(description)
    resetCoroutines()
  }

  private fun setupCoroutines() {
    Dispatchers.setMain(Dispatchers.Unconfined)
  }

  private fun resetCoroutines() {
    Dispatchers.resetMain()
  }
}