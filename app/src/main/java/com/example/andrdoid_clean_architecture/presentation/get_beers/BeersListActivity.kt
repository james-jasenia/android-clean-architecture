package com.example.andrdoid_clean_architecture.presentation.get_beers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.andrdoid_clean_architecture.databinding.ActivityBeersListBinding
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class BeersListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBeersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setupBindings()
    }

//    private fun setupBindings() {
//        lifecycleScope.launchWhenCreated {
//            viewModel.stateFlow.collectLatest {
//                binding.beerTextView.text = it.first().name
//            }
//        }
//    }
}