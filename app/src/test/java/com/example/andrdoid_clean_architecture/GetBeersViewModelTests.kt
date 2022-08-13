package com.example.andrdoid_clean_architecture

import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.use_cases.GetBeersUseCase
import com.example.andrdoid_clean_architecture.presentation.get_beers.GetBeersViewModel
import hilt_aggregated_deps._com_example_andrdoid_clean_architecture_presentation_get_beers_GetBeersViewModel_HiltModules_BindsModule
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class GetBeersViewModelTests {

    @Test
    fun test_getBeers_propagatesBeersList_toStateFlow_onSuccess() = runBlocking {
        val beer = Beer("anyName", "anyImageUrl", "anyDescription", emptyList())
        val expectedResult = listOf(beer, beer)

        val mockUseCase = mock<GetBeersUseCase>()
        whenever(mockUseCase()).thenReturn(Result.success(expectedResult))

        val sut = GetBeersViewModel(mockUseCase)
        val results = mutableListOf<List<Beer>>()
        val job = launch {
            sut.stateFlow.toList(results)
        }

        sut.getBeers()

        assertTrue(results.isEmpty())
        job.cancel()
    }
}