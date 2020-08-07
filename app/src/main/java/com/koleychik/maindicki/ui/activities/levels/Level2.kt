package com.koleychik.maindicki.ui.activities.levels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.animation.Anim_answer
import com.koleychik.maindicki.ui.activities.AfterLevelActivity
import com.koleychik.maindicki.ui.viewModel.Level2ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class Level2 : AppCompatActivity() {

    private lateinit var mainLL: View
    private lateinit var animYes: View
    private lateinit var animNo: View
    private lateinit var layout1: View
    private lateinit var layout2: View
    private lateinit var layout3: View

    private lateinit var btnCheck: Button
    private lateinit var btnDelete: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btn10: Button
    private lateinit var btn11: Button
    private lateinit var btn12: Button
    private lateinit var btn13: Button
    private lateinit var btn14: Button
    private lateinit var btn15: Button

    private lateinit var word1: TextView
    private lateinit var word2: TextView
    private lateinit var apple: TextView

    private lateinit var sheep: ImageView

    val style = Style()

    val listBtn = mutableListOf<Button>()

    private lateinit var viewModel: Level2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level2)

        viewModel = ViewModelProvider(this)[Level2ViewModel::class.java]
        viewModel.makeItFirst()

        init()
        setStyle()

        makeOnClickListener()
        makeOnClickForBtn()
        subscribe()

        giveNewWords()
    }

    private fun giveNewWords() = CoroutineScope(Dispatchers.Main).launch {
        val mas = viewModel.giveNewWords()
        when (mas.size) {
            5 -> {
                layout1.visibility = View.VISIBLE
                layout2.visibility = View.GONE
                layout3.visibility = View.GONE
            }
            10 -> {
                layout1.visibility = View.VISIBLE
                layout2.visibility = View.VISIBLE
                layout3.visibility = View.GONE
            }
            else -> {
                layout1.visibility = View.VISIBLE
                layout2.visibility = View.VISIBLE
                layout3.visibility = View.VISIBLE
            }
        }

        var number = 0
        for (i in listBtn) {
            if (number == mas.size) break

            i.text = mas[number].toString()
            number++
        }
    }

    private fun makeOnClickListener() {
        val onClickListener = View.OnClickListener {
            when (it.id) {
                R.id.buttondelete -> makeDelete()
                R.id.buttonsend -> makeCheck()
            }
        }
        btnCheck.setOnClickListener(onClickListener)
        btnDelete.setOnClickListener(onClickListener)
    }

    private fun makeDelete() {
        val stringBuilder = StringBuilder(viewModel.word.value)

        if (stringBuilder.isNotEmpty()) {
            for (i in listBtn) {
                if (i.text.toString() == stringBuilder[stringBuilder.length - 1].toString()) {
                    setEnableTrue(i)
                    break
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
            viewModel.word.value = stringBuilder.toString()
        }
    }

    private fun setEnableFalse(btn: View) {
        style.setEnableFalse(btn)
    }

    private fun setEnableTrue(btn: View) {
        style.setEnableTrue(btn)
    }

    private fun setEnableTrue(list: MutableList<Button>) {
        style.setEnableTrue(list)
    }

    private fun subscribe() {
        viewModel.wordRus.observe(this, Observer {
            word1.text = it
        })

        viewModel.numberApple.observe(this, Observer {
            apple.text = it.toString()
        })

        viewModel.word.observe(this, Observer {
            word2.text = it
        })
    }

    private fun makeCheck() {
        setEnableTrue(list = listBtn)
        when (viewModel.makeCheck()) {
            -1 -> {
                animationFalse()
                giveNewWords()
            }
            0 -> {
                animationTrue()
                giveNewWords()
            }
            1 -> {
                val intent = Intent(this@Level2, AfterLevelActivity::class.java)
                intent.putExtra(Keys.BEFORE_LEVEL, 2)
                intent.putExtra(Keys.GET_APPLE, viewModel.numberApple.value)
                Log.d("startLevel", viewModel.numberApple.value.toString())
                startActivity(intent)
            }
        }
    }

    private fun giveHelp() {
        if (viewModel.checkHelp()) {

        }
    }

    private fun makeOnClickForBtn() {
        val onClickListener = View.OnClickListener {
            setEnableFalse(it)
            val btn = it as Button
            viewModel.makeOnClickForButton(btn.text.toString())
        }
        btn1.setOnClickListener(onClickListener)
        btn2.setOnClickListener(onClickListener)
        btn3.setOnClickListener(onClickListener)
        btn4.setOnClickListener(onClickListener)
        btn5.setOnClickListener(onClickListener)
        btn6.setOnClickListener(onClickListener)
        btn7.setOnClickListener(onClickListener)
        btn8.setOnClickListener(onClickListener)
        btn9.setOnClickListener(onClickListener)
        btn10.setOnClickListener(onClickListener)
        btn11.setOnClickListener(onClickListener)
        btn12.setOnClickListener(onClickListener)
        btn13.setOnClickListener(onClickListener)
        btn14.setOnClickListener(onClickListener)
        btn15.setOnClickListener(onClickListener)
    }

    private fun setStyleForTextView(listTv: MutableList<TextView>) = CoroutineScope(Dispatchers.Main).launch {
        val style = Style()

        style.setTV(listTv, mutableListOf())
        style.setBtn(listTv)
    }

    private fun setStyle() {
        val listTV = mutableListOf<TextView>()

        listTV.add(btn1)
        listTV.add(btn2)
        listTV.add(btn3)
        listTV.add(btn4)
        listTV.add(btn5)
        listTV.add(btn6)
        listTV.add(btn7)
        listTV.add(btn8)
        listTV.add(btn9)
        listTV.add(btn10)
        listTV.add(btn11)
        listTV.add(btn12)
        listTV.add(btn13)
        listTV.add(btn14)
        listTV.add(btn15)

        listBtn.add(btn1)
        listBtn.add(btn2)
        listBtn.add(btn3)
        listBtn.add(btn4)
        listBtn.add(btn5)
        listBtn.add(btn6)
        listBtn.add(btn7)
        listBtn.add(btn8)
        listBtn.add(btn9)
        listBtn.add(btn10)
        listBtn.add(btn11)
        listBtn.add(btn12)
        listBtn.add(btn13)
        listBtn.add(btn14)
        listBtn.add(btn15)

        listBtn.add(btnCheck)
        listBtn.add(btnDelete)

        listTV.add(word1)
        listTV.add(word2)
        listTV.add(apple)


        style.setBg(mainLL)
        style.setBtn(listBtn)
        style.setTV(listTV, listBtn)
        style.setSheep(sheep)

        listBtn.remove(btnDelete)
        listBtn.remove(btnCheck)
    }

    private fun init() {
        animNo = findViewById(R.id.no)
        animYes = findViewById(R.id.yes)
        mainLL = findViewById(R.id.mainLL)
        layout1 = findViewById(R.id.layout1)
        layout2 = findViewById(R.id.layout2)
        layout3 = findViewById(R.id.layout3)

        btnCheck = findViewById(R.id.buttonsend)
        btnDelete = findViewById(R.id.buttondelete)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)
        btn4 = findViewById(R.id.button4)
        btn5 = findViewById(R.id.button5)
        btn6 = findViewById(R.id.button6)
        btn7 = findViewById(R.id.button7)
        btn8 = findViewById(R.id.button8)
        btn9 = findViewById(R.id.button9)
        btn10 = findViewById(R.id.button10)
        btn11 = findViewById(R.id.button11)
        btn12 = findViewById(R.id.button12)
        btn13 = findViewById(R.id.button13)
        btn14 = findViewById(R.id.button14)
        btn15 = findViewById(R.id.button15)

        word1 = findViewById(R.id.textViewWord1)
        word2 = findViewById(R.id.textViewWord2)
        apple = findViewById(R.id.textAppleLevel2)

        sheep = findViewById(R.id.imageViewSheep)
    }

    private fun animationTrue() {
        Anim_answer(this, animYes, viewModel.getAnswer()).AnimStart()
    }

    private fun animationFalse() {
        Anim_answer(this, animNo, viewModel.getAnswer()).AnimStart()
    }

}