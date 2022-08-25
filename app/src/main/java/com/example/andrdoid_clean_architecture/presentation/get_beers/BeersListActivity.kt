package com.example.andrdoid_clean_architecture.presentation.get_beers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.view.menu.MenuView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.andrdoid_clean_architecture.R
import com.example.andrdoid_clean_architecture.databinding.ActivityBeersListBinding
import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.squareup.picasso.Picasso
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
        setupView()
        setupBindings()
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val divider = DividerItemDecoration(binding.recyclerView.context, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(divider)
    }

    private fun setupBindings() {
        lifecycleScope.launchWhenCreated {
            viewModel.stateFlow.collectLatest {
                when(it) {
                    is GetBeersViewState.Success ->
                        binding.recyclerView.adapter = constructBeerListAdapter(it.beers as MutableList)
                    is GetBeersViewState.Failure ->
                        Log.d("d", "d")
//                        binding.beerTextView.text = it.errorMessage
                    is GetBeersViewState.Loading ->
                        Log.d("d", "d")
//                        binding.beerTextView.text = "Loading"
                }
            }
        }
    }

    private fun constructBeerListAdapter(list: MutableList<BeerCellModel>): BeerListAdapter {
        val adapter = BeerListAdapter()
        adapter.submitList(list)
        return adapter
    }
}

data class BeerCellModel(
    val title: String,
    val imageUrl: String
)

class BeerListAdapter: ListAdapter<BeerCellModel, BeerListAdapter.BeerViewHolder>(BeerDiffCallback) {

    class BeerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val heading = itemView.findViewById<TextView>(R.id.headingTextView)
        private val imageView = itemView.findViewById<ImageView>(R.id.beerImageView)

        fun bind(model: BeerCellModel) {
            heading.text = model.title
            Picasso.get()
                .load(model.imageUrl)
                .resize(50, 50)
                .centerCrop()
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.beer_cell, parent, false)
        return BeerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        var beer = getItem(position)
        holder.bind(beer)
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