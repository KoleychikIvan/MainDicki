package com.koleychik.maindicki.ui.viewHolders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.CheckWordToFailedSymbols
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.models.WordModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShowAllWordsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)) {

    private val textCount: TextView = itemView.findViewById(R.id.textnumber)
    private val wordEng: TextView = itemView.findViewById(R.id.englishword)
    private val wordRus: TextView = itemView.findViewById(R.id.russianword)

    lateinit var model: WordModel

    fun bind(newModel: WordModel, position: Int) {
        model = newModel

        textCount.text = (position + 1).toString()
        wordEng.text = model.english
        wordRus.text = model.russian
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