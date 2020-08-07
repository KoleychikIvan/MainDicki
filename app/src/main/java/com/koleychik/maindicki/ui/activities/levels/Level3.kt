package com.koleychik.maindicki.ui.activities.levels

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.animation.Anim_answer
import com.koleychik.maindicki.ui.activities.AfterLevelActivity
import com.koleychik.maindicki.ui.viewModel.Level3ViewModel
import kotlinx.coroutines.*
import java.util.*

class Level3 : AppCompatActivity() {

    private lateinit var mainLL : View
    private lateinit var layoutWithView : View
    private lateinit var layoutYes : View
    private lateinit var layoutNo : View

    private lateinit var layout1: FrameLayout
    private lateinit var layout2: FrameLayout
    private lateinit var layout3: FrameLayout
    private lateinit var layout4: FrameLayout
    private lateinit var layout5: FrameLayout

    private lateinit var letter1 : TextView
    private lateinit var letter2 : TextView
    private lateinit var letter3 : TextView
    private lateinit var letter4 : TextView
    private lateinit var letter5 : TextView
    private lateinit var letter6 : TextView
    private lateinit var letter7 : TextView
    private lateinit var letter8 : TextView
    private lateinit var letter9 : TextView
    private lateinit var letter10 : TextView
    private lateinit var letter11 : TextView
    private lateinit var letter12 : TextView
    private lateinit var letter13 : TextView
    private lateinit var letter14 : TextView
    private lateinit var letter15 : TextView

    private lateinit var timerText : TextView
    private lateinit var word1 : TextView
    private lateinit var apple : TextView

    private lateinit var viewModel : Level3ViewModel

    private val listTv = mutableListOf<TextView>()
    private lateinit var listMainTv : MutableList<TextView>

    private val mainListAnswer = mutableListOf<String>()

    private lateinit var job : Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level3)

        viewModel = ViewModelProvider(this)[Level3ViewModel::class.java]
        viewModel.makeItFirst()

        init()
        setStyle()
        subscribe()
        startTimer()
    }

    private fun makeButton(place : Int, letter : String){
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        val btn_apple = TextView(this)
        btn_apple.textSize = 30f
        btn_apple.text = letter
        btn_apple.setBackgroundResource(R.drawable.apple64)
        btn_apple.setTextColor(resources.getColor(R.color.colorDark, null))
        btn_apple.isAllCaps = true
        btn_apple.typeface = Typeface.DEFAULT_BOLD
        btn_apple.setPadding(0, 5, 0, 0)
        btn_apple.gravity = Gravity.CENTER
        btn_apple.visibility = View.GONE
        listTv.add(btn_apple)

//        btn_apple.setOnClickListener {
//            Log.d("text", btn_apple.text.toString())
//        }

        when (place) {
            0 -> layout1.addView(btn_apple, layoutParams)
            1 -> layout2.addView(btn_apple, layoutParams)
            2 -> layout3.addView(btn_apple, layoutParams)
            3 -> layout4.addView(btn_apple, layoutParams)
            4 -> layout5.addView(btn_apple, layoutParams)
        }
    }

    private fun makeNewWord(){

        Log.d("startLevel", "start make New Word")

        layout1.removeAllViews()
        layout2.removeAllViews()
        layout3.removeAllViews()
        layout4.removeAllViews()
        layout5.removeAllViews()
        listTv.clear()

        Log.d("startLevel", "start make New Word 1")

        val mainMas =  viewModel.giveNewWords()

//        clear mainList
        for (i in listMainTv){
            i.text = "_"
        }

//        add to mas
        mainListAnswer.clear()
        for (i in viewModel.getAnswer()) {
            mainListAnswer.add(i.toString())
        }

        val random = Random()
        for (i in mainMas){
            makeButton(random.nextInt(5), i)
        }

        Log.d("startLevel", "start make New Word 2")

        job = startLevel()
    }

    private fun startLevel() = CoroutineScope(Dispatchers.Main).launch {
        val height = mainLL.height
        val times = height * 2

        Log.d("startLevel", listMainTv.size.toString())

        for (i in listMainTv){
            i.visibility = View.VISIBLE
            i.animate().setDuration(times.toLong()).translationY(height.toFloat()).start()
            i.setOnClickListener {
                val btn = it as TextView
                viewModel.makeOnClickForButton(btn.text.toString())
                checkSetTextToTv(btn.text.toString())
                Log.d("startLevel", "start Btn")
            }
            delay(2000)
        }
    }

    private fun checkSetTextToTv(letter : String) = CoroutineScope(Dispatchers.Main).launch{
        if (mainListAnswer.contains(letter)){
            for (i in (0 until mainListAnswer.size)){
                if (mainListAnswer[i] == letter){
                    mainListAnswer[i] = "-1"
                    listMainTv[i].text = letter
                    break
                }
            }
        }
    }

    private fun subscribe(){
        viewModel.isFinish.observe(this, androidx.lifecycle.Observer {
            makeCheck(it)
        })

        viewModel.wordRus.observe(this, androidx.lifecycle.Observer {
            word1.text = it
        })

        viewModel.numberApple.observe(this, androidx.lifecycle.Observer {
            apple.text = it.toString()
        })
    }

    private fun makeCheck(value : Int){
        job.cancel()
        when(value){
            -1 ->{
                val anim = Anim_answer(this, layoutNo, viewModel.getAnswer())
                anim.AnimStart()
                makeNewWord()

            }
            0 ->{
                val anim = Anim_answer(this, layoutYes, viewModel.getAnswer())
                anim.AnimStart()
                makeNewWord()
            }
            1 -> {
                val intent = Intent(this@Level3, AfterLevelActivity::class.java)
                intent.putExtra(Keys.BEFORE_LEVEL, 3)
                intent.putExtra(Keys.GET_APPLE, viewModel.numberApple.value)
                Log.d("startLevel", viewModel.numberApple.value.toString())
                startActivity(intent)
            }
        }
    }

    private fun startTimer() = CoroutineScope(Dispatchers.Main).launch{
        timerText.visibility = View.VISIBLE
        layoutWithView.visibility = View.GONE
        for (i in (5 downTo 0)) {
            timerText.text = i.toString()
            delay(1000)
        }
        timerText.visibility = View.GONE
        layoutWithView.visibility = View.GONE
        makeNewWord()
    }

    private fun setStyle(){
        val listTV = mutableListOf<TextView>()

        listTV.add(letter1)
        listTV.add(letter2)
        listTV.add(letter3)
        listTV.add(letter4)
        listTV.add(letter5)
        listTV.add(letter6)
        listTV.add(letter7)
        listTV.add(letter8)
        listTV.add(letter9)
        listTV.add(letter10)
        listTV.add(letter11)
        listTV.add(letter12)
        listTV.add(letter13)
        listTV.add(letter14)
        listTV.add(letter15)
        listTV.add(apple)

        val style = Style()

        listMainTv = listTV
        listMainTv.remove(apple)

        style.setTV(listTV, mutableListOf())
        style.setBg(mainLL)
    }

    private fun init(){
        mainLL = findViewById(R.id.mainLL)
        layoutWithView = findViewById(R.id.layoutWithView)

        word1 = findViewById(R.id.word1)
        apple = findViewById(R.id.textViewApple)
        timerText = findViewById(R.id.timerText)

        letter1 = findViewById(R.id.text1_1)
        letter2 = findViewById(R.id.text1_2)
        letter3 = findViewById(R.id.text1_3)
        letter4 = findViewById(R.id.text1_4)
        letter5 = findViewById(R.id.text1_5)
        letter6 = findViewById(R.id.text1_6)
        letter7 = findViewById(R.id.text1_7)
        letter8 = findViewById(R.id.text1_8)
        letter9 = findViewById(R.id.text1_9)
        letter10 = findViewById(R.id.text1_10)
        letter11 = findViewById(R.id.text1_11)
        letter12 = findViewById(R.id.text1_12)
        letter13 = findViewById(R.id.text1_13)
        letter14 = findViewById(R.id.text1_14)
        letter15 = findViewById(R.id.text1_15)

        layout1 = findViewById(R.id.FRL1)
        layout2 = findViewById(R.id.FRL2)
        layout3 = findViewById(R.id.FRL3)
        layout4 = findViewById(R.id.FRL4)
        layout5 = findViewById(R.id.FRL5)

        layoutYes = findViewById(R.id.yes)
        layoutNo = findViewById(R.id.no)
    }
}