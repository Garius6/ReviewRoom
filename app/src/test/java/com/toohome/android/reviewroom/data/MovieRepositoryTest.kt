package com.toohome.android.reviewroom.data

import android.widget.ImageView
import com.toohome.android.reviewroom.data.model.Comment
import com.toohome.android.reviewroom.data.model.Movie
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MovieRepositoryTest {

    @MockK
    lateinit var movieDataSource: MovieDataSource

    @MockK
    lateinit var iv: ImageView

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun getMovies(): Unit = runBlocking {
        val m = listOf(Movie(1, "Movie 1", ""), Movie(2, "Movie 2", ""))

        coEvery { movieDataSource.getMovies() } returns Response.success(m)

        val sut = UserMovieRepository(movieDataSource)
        assertEquals(SuccessResult(m), sut.getMovies())

        coVerify { movieDataSource.getMovies() }
    }

    @Test
    fun getMovie(): Unit = runBlocking {
        val m = Movie(1, "Movie 1", "")

        coEvery { movieDataSource.getMovie(1) } returns Response.success(m)

        val sut = UserMovieRepository(movieDataSource)
        assertEquals(SuccessResult(m), sut.getMovie(1))

        coVerify { movieDataSource.getMovie(1) }
    }

    @Test
    fun newCommentForMovie(): Unit = runBlocking {
        val c = Comment(1, "Movie 1 is good")

        coEvery { movieDataSource.createCommentForMovie(1, c) } just Runs

        val sut = UserMovieRepository(movieDataSource)
        sut.newCommentForMovie(1, c)

        coVerify { movieDataSource.createCommentForMovie(1, c) }
    }

    @Test
    fun getComments(): Unit = runBlocking {
        val c = listOf(Comment(0, "Movie 0 is good"))

        coEvery { movieDataSource.getComments(1) } returns Response.success(c)

        val sut = UserMovieRepository(movieDataSource)

        assertEquals(SuccessResult(c), sut.getComments(1))

        coVerify { movieDataSource.getComments(1) }
    }

    @Test
    fun loadPostIntoImageView(): Unit = runBlocking {
        val m = Movie(0, "", "Correct url")

        coEvery {
            movieDataSource.loadPosterIntoViewWithPlaceholder(
                m.posterUrl,
                into = iv
            )
        } just Runs

        val sut = UserMovieRepository(movieDataSource)
        sut.loadPostIntoImageView(m, iv)

        coVerify { movieDataSource.loadPosterIntoViewWithPlaceholder(m.posterUrl, into = iv) }
    }
}
