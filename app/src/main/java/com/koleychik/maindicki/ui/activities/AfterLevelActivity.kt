package com.koleychik.maindicki.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.coroutines.WorkWithApple
import com.koleychik.maindicki.ui.activities.levels.Level1
import com.koleychik.maindicki.ui.activities.levels.Level2
import com.koleychik.maindicki.ui.activities.levels.Level3
import com.koleychik.maindicki.ui.activities.levels.Level4

class AfterLevelActivity : AppCompatActivity() {

    private lateinit var mainLL : View

    private lateinit var sheep : ImageView
    private lateinit var imgInfo : ImageView

    private lateinit var textAppleMain : TextView
    private lateinit var apple : TextView

    private lateinit var text : TextView

    private lateinit var btnTryAgain : Button
    private lateinit var btnChooseAnotherWord : Button
    private lateinit var btnExit : Button

    private var appleNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_level)

        init()
        setStyle()
        setApple()
        setText()
        onClick()
    }

    private fun onClick(){
        val onClickListener = View.OnClickListener{
            when(it.id){
                R.id.buttonReturn_else -> tryGetIntent()
                R.id.buttonChoose_another_words -> startBeforeLevel()
                R.id.buttonExit -> startActivity(Intent(this@AfterLevelActivity, MainActivity::class.java))
            }
        }

        btnExit.setOnClickListener(onClickListener)
        btnChooseAnotherWord.setOnClickListener(onClickListener)
        btnTryAgain.setOnClickListener(onClickListener)
    }

    private fun startBeforeLevel(){
        val intent = Intent(this@AfterLevelActivity, BeforeLevelActivity::class.java)
        intent.putExtra(Keys.BEFORE_LEVEL, intent.getIntExtra(Keys.BEFORE_LEVEL, 1))
        startActivity(intent)
    }

    private fun tryGetIntent(){
        when(intent.getIntExtra(Keys.BEFORE_LEVEL, 1)){
            1 -> startActivity(Intent(this@AfterLevelActivity, Level1::class.java))
            2 -> startActivity(Intent(this@AfterLevelActivity, Level2::class.java))
            3 -> startActivity(Intent(this@AfterLevelActivity, Level3::class.java))
            4 -> startActivity(Intent(this@AfterLevelActivity, Level4::class.java))
        }
    }

    private fun setText(){
        when {
            appleNumber == 0 -> {
                text.setText(R.string.afterLevel0)
            }
            appleNumber <= 25 -> {
                text.text = (getString(R.string.afterLevelLessThen25) + appleNumber)
            }
            else -> {
                text.text = (getString(R.string.afterLevelGreatingThen25) + appleNumber)
            }
        }
    }

    private fun setApple(){
        appleNumber = intent.getIntExtra(Keys.GET_APPLE, 0)
        textAppleMain.text = appleNumber.toString()

        val workWithApple = WorkWithApple(getSharedPreferences(Keys.APP_NAME_FOR_SPREF, Context.MODE_PRIVATE))
        apple.text = ((workWithApple.getApple()?.toInt() ?: 0) + appleNumber).toString()

        workWithApple.saveApple(apple.text.toString())

        workWithApple.getApple()?.let { Log.d("startLevel", it) }
    }

    private fun setStyle(){
        val listBtn = mutableListOf<Button>()
        val listTv = mutableListOf<TextView>()

        listBtn.add(btnTryAgain)
        listBtn.add(btnChooseAnotherWord)
        listBtn.add(btnExit)

        listTv.add(textAppleMain)
        listTv.add(apple)

        val style = Style()

        style.setBg(mainLL)
        style.setBtn(listBtn)
        style.setTV(listTv, listBtn)
        style.setSheep(sheep)
    }

    private fun init(){
        mainLL = findViewById(R.id.mainLL)

        sheep = findViewById(R.id.imageViewSheep)
        imgInfo = findViewById(R.id.img_info)

        textAppleMain = findViewById(R.id.textViewMainNumber)
        apple = findViewById(R.id.textAppleMainActivity)

        text = findViewById(R.id.textViewMainText)

        btnTryAgain = findViewById(R.id.buttonReturn_else)
        btnChooseAnotherWord = findViewById(R.id.buttonChoose_another_words)
        btnExit = findViewById(R.id.buttonExit)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this@AfterLevelActivity, ChooseLevelActivity::class.java))
    }
}