package com.example.andrdoid_clean_architecture.use_cases

import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.repositories.BeersRepository
import com.example.andrdoid_clean_architecture.domain.use_cases.GetBeersUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class GetBeersUseCaseTests {

    @Test
    fun test_getBeers_returnsListOfBeers_onSuccess() = runBlocking {
        val beer = Beer("anyName", "anyImageUrl", "anyDescription", emptyList())
        val expectedResult = Result.success(listOf(beer, beer))
        val mockRepository = mock<BeersRepository>()
        whenever(mockRepository.getBeers()).thenReturn(expectedResult)

        val sut = GetBeersUseCase(mockRepository)
        val receivedResult = sut.invoke()

        assertThat(receivedResult).isEqualTo(expectedResult)
    }

    @Test
    fun test_getBeers_returnHttpExceptionError_onHttpError() = runBlocking {
        val errorResponse = """{
            "type": "error",
            "message": "anyMessage"
        }"""
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        val exception = HttpException(Response.error<String>(400, errorResponseBody))
        val expectedResult = Result.failure<List<Beer>>(exception)
        val mockRepository = mock<BeersRepository>()
        whenever(mockRepository.getBeers()).thenReturn(expectedResult)

        val sut = GetBeersUseCase(mockRepository)
        var receivedError = sut.invoke()
        assertThat(receivedError).isEqualTo(expectedResult)
    }

    @Test
    fun test_getBeers_returnIOExceptionError_onIOError() = runBlocking {
        val exception = IOException()
        val expectedResult = Result.failure<List<Beer>>(exception)
        val mockRepository = mock<BeersRepository>()
        whenever(mockRepository.getBeers()).thenReturn(expectedResult)

        val sut = GetBeersUseCase(mockRepository)
        var receivedError = sut.invoke()

        assertThat(receivedError).isEqualTo(expectedResult)
    }
}