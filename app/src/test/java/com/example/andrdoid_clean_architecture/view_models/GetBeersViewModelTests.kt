package com.example.andrdoid_clean_architecture.view_models

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.use_cases.GetBeersUseCase
import com.example.andrdoid_clean_architecture.helpers.TestDispatchers
import com.example.andrdoid_clean_architecture.presentation.get_beers.GetBeersViewModel
import com.example.andrdoid_clean_architecture.presentation.get_beers.GetBeersViewState
import hilt_aggregated_deps._com_example_andrdoid_clean_architecture_presentation_get_beers_GetBeersViewModel_HiltModules_BindsModule
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class GetBeersViewModelTests {


    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Test
    fun test_init_state_isLoading() = runBlocking {
        val expectedResult = GetBeersViewState.Loading
        val mockUseCase = mock<GetBeersUseCase>()

        val sut = GetBeersViewModel(mockUseCase, TestDispatchers())
        val receivedResult = sut.stateFlow.first()

        assertTrue(receivedResult == expectedResult)
    }

    @Test
    fun test_getBeers_propagatesBeersList_toStateFlow_onSuccess() = runBlocking {
        val beer = Beer("anyName", "anyImageUrl", "anyDescription", emptyList())
        val expectedResult = listOf(beer, beer)

        val mockUseCase = mock<GetBeersUseCase>()
        whenever(mockUseCase()).thenReturn(Result.success(expectedResult))

        val sut = GetBeersViewModel(mockUseCase, TestDispatchers())
        val results = mutableListOf<GetBeersViewState>()
        val job = launch {
            sut.stateFlow.toList(results)
        }

        assertTrue(results[0] == GetBeersViewState.Loading)
        assertTrue(results[1] == GetBeersViewState.Success(expectedResult))
        job.cancel()
    }
}