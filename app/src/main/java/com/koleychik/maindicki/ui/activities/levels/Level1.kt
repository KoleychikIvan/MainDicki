package com.koleychik.maindicki.ui.activities.levels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.animation.Anim_answer
import com.koleychik.maindicki.ui.activities.AfterLevelActivity
import com.koleychik.maindicki.ui.viewModel.Level1ViewModel

class Level1 : AppCompatActivity() {

    private lateinit var animNo: View
    private lateinit var animYes: View
    private lateinit var mainLL: View

    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button

    private lateinit var word1: TextView
    private lateinit var apple: TextView

    private lateinit var viewModel: Level1ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        viewModel = ViewModelProvider(this)[Level1ViewModel::class.java]

        init()

        setStyle()

        viewModel.makeItFirst()

        makeOnClickToBtn()
        subscribe()
    }

//   private fun makeSetLanguage(){
//        viewModel.isEnglish.value = !viewModel.isEnglish.value!!
//        viewModel.giveNewWords()
//    }

    private fun subscribe(){
        viewModel.wordRus.observe(this, Observer {
            word1.text = it
        })

//        viewModel.wordEng.observe(this, Observer {
//            word1.text = it
//        })

        viewModel.numberApple.observe(this, Observer {
            apple.text = it.toString()
        })

        viewModel.wordAllWords.observe(this, Observer {
            btn1.text = it[0]
            btn2.text = it[1]
            btn3.text = it[2]
        })
//        viewModel.valueHelps.observe(this, Observer {
//
//        })
    }

    private fun makeOnClickToBtn(){
        val onClickListener = View.OnClickListener{
            when(it.id){
                R.id.button1 -> nextStepAfterBtnClick(viewModel.makeOnClickForButton(btn1.text.toString()))
                R.id.button2 -> nextStepAfterBtnClick(viewModel.makeOnClickForButton(btn2.text.toString()))
                R.id.button3 -> nextStepAfterBtnClick(viewModel.makeOnClickForButton(btn3.text.toString()))
            }
        }

        btn1.setOnClickListener(onClickListener)
        btn2.setOnClickListener(onClickListener)
        btn3.setOnClickListener(onClickListener)
    }

    private fun nextStepAfterBtnClick(number : Int){
        when(number){
            -1 -> animationFalse()
            0 -> animationTrue()
            1 -> {
                val intent = Intent(this@Level1, AfterLevelActivity::class.java)
                intent.putExtra(Keys.BEFORE_LEVEL, 1)
                intent.putExtra(Keys.GET_APPLE, viewModel.numberApple.value)
                Log.d("startLevel", viewModel.numberApple.value.toString())
                startActivity(intent)
            }
        }
    }

    private fun init() {
        mainLL = findViewById(R.id.mainLL)
        apple = findViewById(R.id.textAppleLevel1)
        word1 = findViewById(R.id.textView)

        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)

        animNo = findViewById(R.id.no)
        animYes = findViewById(R.id.yes)
    }

    private fun animationTrue() {
        Anim_answer(this, animYes, viewModel.getAnswer()).AnimStart()
    }

    private fun animationFalse() {
        Anim_answer(this, animNo, viewModel.getAnswer()).AnimStart()
    }

    private fun setStyle(){
        val listBtn = mutableListOf<Button>()
        val listTV = mutableListOf<TextView>()

        listBtn.add(btn1)
        listBtn.add(btn2)
        listBtn.add(btn3)

        listTV.add(word1)
        listTV.add(apple)

        val style = Style()
        style.setBg(mainLL)
        style.setBtn(listBtn)
        style.setTV(listTV, listBtn)
    }
}