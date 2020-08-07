package com.koleychik.maindicki.ui.viewHolders

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.repositories.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BeforeLevelViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)){

    private val textCount: TextView = itemView.findViewById(R.id.textnumber)
    private val wordEng: TextView = itemView.findViewById(R.id.englishword)
    private val wordRus: TextView = itemView.findViewById(R.id.russianword)

    private val check: ImageView = itemView.findViewById(R.id.imageView)

    lateinit var model: WordModel

//    private val repository = WordRepository(parent.context.applicationContext as Application)


    fun bind(newModel: WordModel, position: Int) {
        model = newModel


        textCount.text = (position + 1).toString()
        wordEng.text = model.english
        wordRus.text = model.russian

        itemView.setOnClickListener {
            if (model.check){
                check.visibility = View.GONE
                model.check = false
            }
            else{
                check.visibility = View.VISIBLE
                model.check = true
            }
        }
    }

    fun setStyle() = GlobalScope.launch {
        val style = Style()

        val listTv = mutableListOf<TextView>()
        listTv.add(wordEng)
        listTv.add(wordRus)
        listTv.add(textCount)
        style.setTV(listTv, mutableListOf())
    }


}