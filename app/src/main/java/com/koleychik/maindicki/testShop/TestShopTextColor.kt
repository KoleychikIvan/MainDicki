package com.koleychik.maindicki.testShop

import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.Singletons.SingletonStyle
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.coroutines.WorkWithApple

class TestShopTextColor(private val listTextView : Collection<TextView>,
                        private val listBtn : Collection<Button>,
                        private val btn : Button, private val apple : TextView) : TestShop {
    private val singleton = SingletonStyle.getInstance()
    private val style = Style()

    private var mainTextColor = singleton.styleTextColor

    private var newNumber = mainTextColor

    private var numberApple = apple.text.toString().toInt()

    override fun returnToMainStyle() {
        singleton.styleTextColor = mainTextColor
    }

    override fun makeNext(){
        newNumber++
        if (newNumber == style.getListSize()){
            newNumber = 0
        }
//        Log.d("style", listTextView.size.toString() + " " + listBtn.size.toString())
        mainAct()
    }

    override fun makeBack(){
        newNumber--
        if (newNumber < 0){
            newNumber = style.getListSize() - 1
        }
        mainAct()
    }

    override fun mainAct(){
        singleton.styleTextColor = newNumber

        if (singleton.isInSetTextColor(newNumber)){
            btn.setText(R.string.have_this_item)
            btn.setOnClickListener {
                makeBuy()
            }
        }
        else{
            btn.setText(R.string.buy)
            btn.setOnClickListener {
                if (numberApple - singleton.priceTextView >= 0){
                    numberApple -= singleton.priceTextView
                    apple.text = numberApple.toString()
                    makeBuy()
                    setApple()
                }
            }
        }

        setStyle()
    }

    private fun setApple(){
        val workWithApple = WorkWithApple(btn.context.getSharedPreferences(Keys.APP_NAME_FOR_SPREF, Context.MODE_PRIVATE))
        workWithApple.saveApple(apple = apple.text.toString())
    }

    override fun setStyle(){
        style.setTV(listTextView, listBtn)
    }

    override fun makeBuy(){
        mainTextColor = newNumber
        singleton.buyTextColor(mainTextColor)
    }
}