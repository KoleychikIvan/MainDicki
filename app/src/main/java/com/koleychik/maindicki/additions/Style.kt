package com.koleychik.maindicki.additions

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.Singletons.SingletonStyle

class Style {

    private var singleton : SingletonStyle = SingletonStyle.getInstance()

    //    blue, black, gold red green purple white

    private val listStyleBtn = listOf(R.drawable.style_btn_blue, R.drawable.style_btn_black,
            R.drawable.style_btn_gold, R.drawable.style_btn_red, R.drawable.style_btn_green,
            R.drawable.style_btn_purple, R.drawable.style_btn_white)

    private val listStyleBtnEnableFalse = listOf(R.drawable.style_btn_blue_click,
            R.drawable.style_btn_black_click, R.drawable.style_btn_gold_click,
            R.drawable.style_btn_red_click, R.drawable.style_btn_green_click,
            R.drawable.style_btn_purple_click, R.drawable.style_btn_white_click)

    private val listStyleColors = listOf(R.color.colorWhite, R.color.colorBlue,
            R.color.colorDark, R.color.colorgold, R.color.colorRed,
            R.color.colorGreen, R.color.colorPurple)

    private val listSheep = listOf(R.drawable.sheep_right, R.drawable.sheep_black,
            R.drawable.sheep_orange, R.drawable.sheep_green, R.drawable.sheep_red,
            R.drawable.sheep_blue, R.drawable.sheep_dark_blue)

    fun getListSize(): Int = listStyleBtn.size

    fun setBtn(listBtn : Collection<View>){
        for (i in listBtn){
            i.setBackgroundResource(listStyleBtn[singleton.styleBtn])
        }
    }

    fun setEdt(listEdt : Collection<EditText>){
        for (i in listEdt){
            i.setBackgroundResource(listStyleBtn[singleton.styleBtn])

            i.setTextColor(listStyleColors[singleton.styleTextColor])
        }
    }

    fun setBg(view : View){
        view.setBackgroundResource(listStyleColors[singleton.styleBg])
    }

    fun setTV(listTV : Collection<TextView>, listBtn : Collection<Button>){
        Log.d("style", listTV.size.toString() + " " + listBtn.size.toString())

        for (i in listTV){
            Log.d("style", "fuck")
            i.setTextColor(i.context.resources.getColor(listStyleColors[singleton.styleTextColor], i.context.resources.newTheme()))
        }

        for (i in listBtn){
            i.setTextColor(i.context.resources.getColor(listStyleColors[singleton.styleTextColor], i.context.resources.newTheme()))
        }
    }

    fun setTV(listTV : Collection<TextView>, listBtn : Collection<Button>, listEdt: Collection<EditText>){
//        Log.d("style", listTV.size.toString() + " " + listBtn.size.toString())

        for (i in listTV){
            Log.d("style", "fuck")
            i.setTextColor(i.context.resources.getColor(listStyleColors[singleton.styleTextColor], i.context.resources.newTheme()))
        }

        for (i in listBtn){
            i.setTextColor(i.context.resources.getColor(listStyleColors[singleton.styleTextColor], i.context.resources.newTheme()))
        }

        for (i in listEdt){
            i.setTextColor(i.context.resources.getColor(listStyleColors[singleton.styleTextColor], i.context.resources.newTheme()))
        }
    }

    fun setSheep(sheep : ImageView){
        sheep.setImageResource(listSheep[singleton.styleSheep])
    }

    fun setEnableFalse(btn : View){
        btn.setBackgroundResource(listStyleBtnEnableFalse[singleton.styleBtn])
        btn.isEnabled = false
    }

    fun setEnableTrue(listBtn : MutableList<Button>){
        for (btn in listBtn){
            btn.setBackgroundResource(listStyleBtn[singleton.styleBtn])
            btn.isEnabled = true
        }
    }

    fun setEnableTrue(btn : View){
        btn.setBackgroundResource(listStyleBtn[singleton.styleBtn])
        btn.isEnabled = true
    }

}