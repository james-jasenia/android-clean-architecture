package com.example.andrdoid_clean_architecture

import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.repositories.BeersRepository
import com.example.andrdoid_clean_architecture.domain.use_cases.GetBeersUseCase
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertFailsWith


class GetBeersUseCaseTests {

    @Test
    fun test_getBeers_returnsListOfBeers_onSuccess() = runBlocking {
        val beer = Beer("anyName", "anyImageUrl", "anyDescription", emptyList())
        val expectedResult = listOf(beer, beer)
        val mockRepository = mock<BeersRepository>()
        whenever(mockRepository.getBeers()).thenReturn(expectedResult)

        val sut = GetBeersUseCase(mockRepository)
        val receivedResult = sut.invoke().first().getOrNull()

        assertTrue(expectedResult == receivedResult)
    }

    @Test
    fun test_getBeers_returnError_onError() = runBlocking {
        val errorResponse = """{
            "type": "error",
            "message": "anyMessage"
        }"""
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        val expectedResult = HttpException(Response.error<String>(400, errorResponseBody))
        val mockRepository = mock<BeersRepository>()
        whenever(mockRepository.getBeers()).then {
            throw expectedResult
        }

        val sut = GetBeersUseCase(mockRepository)
        var receivedError = sut.invoke().first().exceptionOrNull()

        assertTrue(expectedResult == receivedError)
    }
}