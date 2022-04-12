package com.toohome.android.reviewroom.model.api

// class MovieApiFetcherTest {
//    @Test
//    fun testGetMovie() = runBlocking {
//
//        val request: RecordedRequest = takeMockRequest {
//            runBlocking {
//                getMovie(1)
//            }
//        }
//        val resp = request.body
//        assertEquals(request.method, "GET")
//    }
//
//    private fun takeMockRequest(sut: MovieApi.() -> Unit): RecordedRequest {
//        return MockWebServer()
//            .use {
//                it.enqueue(MockResponse().setBody(Gson().toJson(Movie(0, "test retrofit", "/"))))
//                it.start()
//                val url = it.url("/")
//
//                sut()
//
//                it.takeRequest()
//            }
//    }
// }
