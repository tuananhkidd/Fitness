package com.kidd.fitness.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kidd.fitness.R
import com.kidd.fitness.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.kidd.fitness.entity.User
import com.kidd.fitness.extension.inflate
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_load_more,
                parent,
                false
            )
        )
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return SearchViewHolder(
            context!!.inflate(R.layout.item_search,parent,false)
        )
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val searchViewHolder = holder as SearchViewHolder
        val search = getItem(position, User::class.java)!!
        searchViewHolder.itemView.tv_id.text = search.id.toString()
        searchViewHolder.itemView.tv_title.text = search.name
    }

    class SearchViewHolder(view: View) : NormalViewHolder(view)
}