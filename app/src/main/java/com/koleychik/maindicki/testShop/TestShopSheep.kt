package com.koleychik.maindicki.testShop

import android.content.Context
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.Singletons.SingletonStyle
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.coroutines.WorkWithApple

class TestShopSheep(private val sheep : ImageView, private val btn : Button, private val apple : TextView) : TestShop{
    private val singleton = SingletonStyle.getInstance()
    private val style = Style()

    private var mainSheep = singleton.styleSheep

    private var newNumber = mainSheep

    private var numberApple = apple.text.toString().toInt()

    override fun returnToMainStyle() {
        singleton.styleSheep = mainSheep
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
        singleton.styleSheep = newNumber
        if (singleton.isInSetSheep(newNumber)){
            btn.setText(R.string.have_this_item)
            btn.setOnClickListener {
                makeBuy()
            }
        }
        else{
            btn.setText(R.string.buy)
            btn.setOnClickListener {
                if (numberApple - singleton.priceSheep >= 0){
                    numberApple -= singleton.priceSheep
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
        style.setSheep(sheep)
    }

    override fun makeBuy(){
        mainSheep = newNumber
        singleton.buySheep(mainSheep)
    }
}