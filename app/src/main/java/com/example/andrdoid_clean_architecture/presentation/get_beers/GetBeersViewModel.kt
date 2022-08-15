package com.example.andrdoid_clean_architecture.presentation.get_beers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.use_cases.GetBeersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetBeersViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<List<Beer>>(emptyList())
    val stateFlow = _stateFlow.asStateFlow()

    init {
        GlobalScope.launch {
            getBeers()
        }
    }

    suspend fun getBeers() {
        val result = getBeersUseCase()
        result.fold(
            onFailure = {
                Log.d("GetBeersViewModel", "getBeersFailed")
            },
            onSuccess = {
                _stateFlow.value = it
            }
        )
    }
}
