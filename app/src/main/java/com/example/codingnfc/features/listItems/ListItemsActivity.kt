package com.example.codingnfc.features.listItems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codingnfc.ItemElement
import com.example.codingnfc.databinding.ActivityListItemsBinding

class ListItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListItemsBinding
    private lateinit var adapter: ListItemsAdapter // adapter: lien entre donnée (film) et recyclerview
    val viewModel: ListItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemsObserver = Observer<List<ItemElement>> { itemInfo ->
            adapter = ListItemsAdapter(this, itemInfo)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }

        viewModel.itemsLiveData.observe(this, itemsObserver)

        viewModel.readItemsInfo()

    }
}