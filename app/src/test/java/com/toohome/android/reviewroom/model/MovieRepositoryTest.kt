package com.toohome.android.reviewroom.model

import android.widget.ImageView
import com.toohome.android.reviewroom.model.api.MovieApiFetcher
import com.toohome.android.reviewroom.utils.SuccessResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MovieRepositoryTest {

    @MockK
    lateinit var movieApiFetcher: MovieApiFetcher

    @MockK
    lateinit var iv: ImageView

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun getMovies(): Unit = runBlocking {
        val m = listOf(Movie(1, "Movie 1", ""), Movie(2, "Movie 2", ""))

        coEvery { movieApiFetcher.getMovies() } returns Response.success(m)

        val sut = MovieRepository(movieApiFetcher)
        assertEquals(SuccessResult(m), sut.getMovies())

        coVerify { movieApiFetcher.getMovies() }
    }

    @Test
    fun getMovie(): Unit = runBlocking {
        val m = Movie(1, "Movie 1", "")

        coEvery { movieApiFetcher.getMovie(1) } returns Response.success(m)

        val sut = MovieRepository(movieApiFetcher)
        assertEquals(SuccessResult(m), sut.getMovie(1))

        coVerify { movieApiFetcher.getMovie(1) }
    }

    @Test
    fun newCommentForMovie(): Unit = runBlocking {
        val c = Comment(1, "Movie 1 is good")

        coEvery { movieApiFetcher.createCommentForMovie(1, c) } just Runs

        val sut = MovieRepository(movieApiFetcher)
        sut.newCommentForMovie(1, c)

        coVerify { movieApiFetcher.createCommentForMovie(1, c) }
    }

    @Test
    fun getComments(): Unit = runBlocking {
        val c = listOf(Comment(0, "Movie 0 is good"))

        coEvery { movieApiFetcher.getComments(1) } returns Response.success(c)

        val sut = MovieRepository(movieApiFetcher)

        assertEquals(SuccessResult(c), sut.getComments(1))

        coVerify { movieApiFetcher.getComments(1) }
    }

    @Test
    fun loadPostIntoImageView(): Unit = runBlocking {
        val m = Movie(0, "", "Correct url")

        coEvery {
            movieApiFetcher.loadPosterIntoViewWithPlaceholder(
                m.posterUrl,
                into = iv
            )
        } just Runs

        val sut = MovieRepository(movieApiFetcher)
        sut.loadPostIntoImageView(m, iv)

        coVerify { movieApiFetcher.loadPosterIntoViewWithPlaceholder(m.posterUrl, into = iv) }
    }
}
