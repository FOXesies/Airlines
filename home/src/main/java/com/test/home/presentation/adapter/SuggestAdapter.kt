package com.test.home.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.test.home.domain.model.Suggest
import com.test.home.presentation.OnSuggestClickListener
import com.test.home.util.suggestAdapterDelegate

class SuggestAdapter(onSuggestClickListener: OnSuggestClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val delegatesManager = AdapterDelegatesManager<List<Suggest>>()
        .addDelegate(suggestAdapterDelegate(onSuggestClickListener))

    var suggests: List<Suggest> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(suggests, position, holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(suggests, position)
    }

    override fun getItemCount(): Int {
        return suggests.size
    }
}