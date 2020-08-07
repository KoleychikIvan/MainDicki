package com.koleychik.maindicki.ui.spinners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Style
import java.util.*

class LanguageSpinnerAdapter(context: Context, modelList: MutableList<LanguageSpinnerModel>)
    : ArrayAdapter<LanguageSpinnerModel>(context, 0, modelList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View? {

        val itemView = LayoutInflater.from(context).inflate(R.layout.item_spiner, parent, false)

        val languageName : TextView = itemView.findViewById(R.id.languageName)
        languageName.text = context.getString((getItem(position) as LanguageSpinnerModel).language)

        val style = Style()
        style.setTV(mutableListOf(languageName), mutableListOf())

        return itemView
    }

}