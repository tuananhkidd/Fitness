package com.kidd.fitness.extension

import androidx.recyclerview.widget.RecyclerView
import com.kidd.fitness.base.adapter.BaseRecyclerView
import com.kidd.fitness.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.kidd.fitness.base.adapter.RecyclerViewAdapter

infix fun BaseRecyclerView.onLoadingMore(init: EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener.() -> Unit) {
    setOnLoadingMoreListener(object : EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
        override fun onLoadMore() {
            init(this)
        }
    })
}

infix fun BaseRecyclerView.onItemClick(init : OnItemClickListenerWrapper.() -> Unit){
    val wrapper = OnItemClickListenerWrapper().apply { init() }
    setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener{
        override fun onItemClick(
            adapter: RecyclerView.Adapter<*>,
            viewHolder: RecyclerView.ViewHolder?,
            viewType: Int,
            position: Int
        ) {
            wrapper.onItemClick?.invoke(adapter,viewHolder,viewType,position)
        }
    })
}