package com.koleychik.maindicki.ui.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.ui.viewHolders.ShowAllWordsViewHolder

class ShowWordsAdapter : PagingDataAdapter<WordModel, ShowAllWordsViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAllWordsViewHolder {

        val showAllWordsViewHolder = ShowAllWordsViewHolder(parent)
        showAllWordsViewHolder.setStyle()
        return showAllWordsViewHolder
    }

    override fun onBindViewHolder(holder: ShowAllWordsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }



    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<WordModel>() {
            override fun areItemsTheSame(oldItem: WordModel, newItem: WordModel): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: WordModel, newItem: WordModel): Boolean =
                oldItem == newItem
        }
    }

}