package com.koleychik.maindicki.testShop

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.Singletons.SingletonStyle
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.coroutines.WorkWithApple

class TestShopBg(private val view : View, private val btn : Button, private val apple : TextView) : TestShop{
    private val singleton = SingletonStyle.getInstance()
    private val style = Style()

    private var mainBg = singleton.styleBg

    private var newNumber = mainBg

    private var numberApple = apple.text.toString().toInt()

    override fun makeNext(){
        newNumber++
        if (newNumber == style.getListSize()){
            newNumber = 0
        }
        mainAct()
    }

    override fun makeBack(){
        Log.d("style", mainBg.toString())

        newNumber--
        if (newNumber < 0){
            newNumber = style.getListSize() - 1
        }
        Log.d("style", newNumber.toString())
        mainAct()
    }

    override fun mainAct(){
        if (singleton.isInSetBg(newNumber)){
            btn.setText(R.string.have_this_item)
            btn.setOnClickListener {
                makeBuy()
            }
        }
        else{
            btn.setText(R.string.buy)
            btn.setOnClickListener {
                if (numberApple - singleton.priceBg >= 0){
                    numberApple -= singleton.priceBg
                    apple.text = numberApple.toString()
                    makeBuy()
                    setApple()
                }
            }
        }
        singleton.styleBg = newNumber

        setStyle()
    }

    private fun setApple(){
        val workWithApple = WorkWithApple(view.context.getSharedPreferences(Keys.APP_NAME_FOR_SPREF, Context.MODE_PRIVATE))
        workWithApple.saveApple(apple = apple.text.toString())
    }

    override fun returnToMainStyle() {
        singleton.styleBg = mainBg
    }

    override fun setStyle(){
        style.setBg(view)
    }

    override fun makeBuy(){
        mainBg = newNumber
        singleton.buyBg(mainBg)
    }
}