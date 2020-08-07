package com.koleychik.maindicki.testShop

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.Singletons.SingletonStyle
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.coroutines.WorkWithApple

class TestShopBtn(private val list : Collection<View>, private val btn : Button, private val apple : TextView) : TestShop{
    private val singleton = SingletonStyle.getInstance()
    private val style = Style()

    private var mainBtn = singleton.styleBtn

    private var newNumber = mainBtn

    private var numberApple = apple.text.toString().toInt()

    override fun returnToMainStyle() {
        singleton.styleBtn = mainBtn
    }

    override fun makeNext(){
        newNumber++
        if (newNumber == style.getListSize()){
            newNumber = 0
        }
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
        singleton.styleBtn = newNumber
        if (singleton.isInSetBtn(newNumber)){
            btn.setText(R.string.have_this_item)
            btn.setOnClickListener {
                makeBuy()
            }
        }
        else{
            btn.setText(R.string.buy)
            btn.setOnClickListener {
                if (numberApple - singleton.priceBtn >= 0){
                    numberApple -= singleton.priceBtn
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
        style.setBtn(list)
    }

    override fun makeBuy(){
        mainBtn = newNumber
        singleton.buyBtn(mainBtn)
    }
}