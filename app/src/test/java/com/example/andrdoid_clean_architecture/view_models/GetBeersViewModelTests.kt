package com.example.andrdoid_clean_architecture.view_models

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.use_cases.GetBeersUseCase
import com.example.andrdoid_clean_architecture.helpers.TestDispatchers
import com.example.andrdoid_clean_architecture.presentation.get_beers.GetBeersViewModel
import com.example.andrdoid_clean_architecture.presentation.get_beers.GetBeersViewState
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals


class GetBeersViewModelTests {

    private val testDispatchers = TestDispatchers()

    @Test
    fun test_init_state_isLoading() = runTest {
        val expectedResult = GetBeersViewState.Loading
        val mockUseCase = mock<GetBeersUseCase>()

        val sut = GetBeersViewModel(mockUseCase, testDispatchers)
        val receivedResult = sut.stateFlow.first()

        assertTrue(receivedResult == expectedResult)
    }

    @Test
    fun test_getBeers_propagatesBeersList_toStateFlow_onSuccess() = runTest {
        val beer = Beer("anyName", "anyImageUrl", "anyDescription", emptyList())
        val expectedResult = listOf(beer, beer)
        val mockUseCase = mock<GetBeersUseCase>()
        whenever(mockUseCase.invoke())
            .thenReturn(Result.success(expectedResult))

        val sut = GetBeersViewModel(mockUseCase, testDispatchers)
        sut.stateFlow.test {
            val emission = awaitItem()
            assertThat(emission)
                .isEqualTo(GetBeersViewState.Success(expectedResult))
        }
    }
}