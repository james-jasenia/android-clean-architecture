package com.example.andrdoid_clean_architecture.presentation.get_beers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.view.menu.MenuView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.andrdoid_clean_architecture.R
import com.example.andrdoid_clean_architecture.databinding.ActivityBeersListBinding
import com.example.andrdoid_clean_architecture.domain.model.Beer
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
//                        binding.recyclerView.adapter =
                    is GetBeersViewState.Failure ->
//                        binding.beerTextView.text = it.errorMessage
                    is GetBeersViewState.Loading ->
//                        binding.beerTextView.text = "Loading"
                }
            }
        }
    }
}

data class BeerCellModel(
    val title: String
)

class BeerListAdapter: ListAdapter<BeerCellModel, BeerListAdapter.BeerViewHolder>(BeerDiffCallback) {

    class BeerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val heading = itemView.findViewById<TextView>(R.id.headingTextView)

        fun configure(model: BeerCellModel) {
            heading.text = model.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.beer_cell, parent, false)
        return BeerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        var beer = getItem(position)
        holder.configure(beer)
    }
}

object BeerDiffCallback : DiffUtil.ItemCallback<BeerCellModel>() {
    override fun areItemsTheSame(oldItem: BeerCellModel, newItem: BeerCellModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BeerCellModel, newItem: BeerCellModel): Boolean {
        return oldItem.title == newItem.title
    }

}