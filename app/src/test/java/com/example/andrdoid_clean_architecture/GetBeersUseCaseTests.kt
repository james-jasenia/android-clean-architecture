package com.example.andrdoid_clean_architecture

import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.repositories.BeersRepository
import com.example.andrdoid_clean_architecture.domain.use_cases.GetBeersUseCase
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

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
}