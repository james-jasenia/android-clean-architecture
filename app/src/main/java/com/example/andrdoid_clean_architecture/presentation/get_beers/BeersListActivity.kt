package com.example.andrdoid_clean_architecture.presentation.get_beers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.andrdoid_clean_architecture.databinding.ActivityBeersListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeersListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBeersListBinding
    private val viewModel: GetBeersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBindings()
    }

    private fun setupBindings() {
        lifecycleScope.launchWhenCreated {
            viewModel.stateFlow.collectLatest {
                when(it) {
                    is GetBeersViewState.Success ->
                        binding.beerTextView.text =  it.beers.firstOrNull()?.name
                    is GetBeersViewState.Failure ->
                        binding.beerTextView.text = it.errorMessage
                    is GetBeersViewState.Loading ->
                        binding.beerTextView.text = "Loading"
                }
            }
        }
    }
}