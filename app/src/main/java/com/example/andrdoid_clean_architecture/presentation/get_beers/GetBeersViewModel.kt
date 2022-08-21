package com.example.andrdoid_clean_architecture.presentation.get_beers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.use_cases.GetBeersUseCase
import com.example.andrdoid_clean_architecture.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetBeersViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<GetBeersViewState>(GetBeersViewState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        getBeers()
    }

    private fun getBeers() {
        viewModelScope.launch(dispatchers.io) {
            val result = getBeersUseCase()
            withContext(dispatchers.main) {
                result.fold(
                    onFailure = {
                        _stateFlow.value = GetBeersViewState.Failure("Generic Network Error.")
                    },
                    onSuccess = {
                        _stateFlow.value = GetBeersViewState.Success(it)
                    }
                )
            }
        }
    }
}

sealed class GetBeersViewState {
    data class Success(val beers: List<Beer>) : GetBeersViewState()
    data class Failure(val errorMessage: String) : GetBeersViewState()
    object Loading : GetBeersViewState()
}



