package com.koleychik.maindicki.ui.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.ui.viewHolders.BeforeLevelViewHolder

class BeforeLevelAdapter : PagingDataAdapter<WordModel, BeforeLevelViewHolder>(diffCallback) {

    private val listViewHolder = mutableListOf<BeforeLevelViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeforeLevelViewHolder {
        val beforeLevelViewHolder = BeforeLevelViewHolder(parent)
        beforeLevelViewHolder.setStyle()
        listViewHolder.add(beforeLevelViewHolder)
        return beforeLevelViewHolder
    }

    override fun onBindViewHolder(holder: BeforeLevelViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<WordModel>() {
            override fun areItemsTheSame(oldItem: WordModel, newItem: WordModel): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: WordModel, newItem: WordModel): Boolean =
                    oldItem == newItem
        }
    }

    fun whenAppDestroy(): MutableList<WordModel>{
        val list= mutableListOf<WordModel>()
        for ( i in listViewHolder){
            if (i.model.check) {
                list.add(i.model)
            }
        }
        return list
    }
}