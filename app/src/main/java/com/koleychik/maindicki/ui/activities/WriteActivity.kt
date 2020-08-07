package com.koleychik.maindicki.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Addition
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.SheepGoing
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.coroutines.WorkWithApple
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.ui.viewModel.WriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteActivity : AppCompatActivity() {

    private val sheepGoing = SheepGoing(this@WriteActivity, Keys.WRITE_FOR_SPREF, R.string.dialog_first_time_write_activity)

    private lateinit var viewModel: WriteViewModel

    private lateinit var mainLL: View
    private lateinit var textFailedSymbol: TextView
    private lateinit var sheep: ImageView
    private lateinit var imgInfo: ImageView
    private lateinit var edtWord1: EditText
    private lateinit var edtWord2: EditText
    private lateinit var apple: TextView
    private lateinit var textAllWords: TextView
    private lateinit var btnSave: Button
    private lateinit var btnEnd: Button
    private lateinit var showAllWords: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        viewModel = ViewModelProvider(this)[WriteViewModel::class.java]

        init()
        setStyle()
//        viewModel.makeItFirst()
        workWithItInCoroutinesInOnResume()
        makeInfo()

        makeObserver()

        setApple()
        sheepGoing.checkIsItFirstTime()
        setOnClickListener()
    }

    private fun makeObserver(){
        viewModel.liveDataListSize.observe(this, Observer {
            setTextAllWords(it)
        })
    }

    private fun workWithItInCoroutinesInOnResume() = CoroutineScope(Dispatchers.Main).launch{
        val dbSize = viewModel.getSizeDb()
        setTextAllWords(dbSize)

        btnEnd.isEnabled = dbSize >= 3
    }

    private fun setOnClickListener() {
        val onClickListener = View.OnClickListener {
            when (it.id) {
                R.id.buttonwrite -> makeAdd()
                R.id.buttonend -> {
                    makeAdd(true)
                    startActivity(Intent(this@WriteActivity, ChooseLevelActivity::class.java))
                }
                R.id.buttonallwords -> startActivity(Intent(this@WriteActivity, ShowAllWordsActivity::class.java))
            }
        }

        btnSave.setOnClickListener(onClickListener)
        btnEnd.setOnClickListener(onClickListener)
        showAllWords.setOnClickListener(onClickListener)
    }

    private fun makeAdd(isEnd: Boolean = false ) {
        val wordEng = edtWord1.text.toString().trim()
        val wordRus = edtWord2.text.toString().trim()

        if (checkFailedSymbols(wordEng, wordRus, isEnd)) return

        edtWord1.setText("")
        edtWord2.setText("")
        viewModel.liveDataListSize.value = viewModel.liveDataListSize.value?.plus(1)

        viewModel.insert(WordModel(russian = wordRus, english = wordEng, check = false, time = Addition.getTime()))
    }

    private fun checkFailedSymbols(word1: String, word2: String, isEnd: Boolean): Boolean {
        if (viewModel.makeCheckWordToFailed(word1) || viewModel.makeCheckWordToFailed(word2)) {
            if (isEnd) return true
            textFailedSymbol.setText(R.string.failedSymbol)
            textFailedSymbol.visibility = View.VISIBLE
            return true
        }
//        if (viewModel.list.contains(word1))
        textFailedSymbol.visibility = View.GONE
        return false
    }

    private fun makeInfo() {
        imgInfo.setOnClickListener {
            sheepGoing.startSheep()
        }
    }

    private fun setTextAllWords(number : Int) = CoroutineScope(Dispatchers.Main).launch{
        checkBtnEnable(number)
        val text = getString(R.string.allWords) + number
        textAllWords.text = text
    }

    private fun checkBtnEnable(number: Int){
        btnEnd.isEnabled = number >= 3
    }

    private fun setStyle() {
        val listBtn = mutableListOf<Button>()
        val listTV = mutableListOf<TextView>()
        val listEdt = mutableListOf<EditText>()

        listBtn.add(btnSave)
        listBtn.add(btnEnd)
        listBtn.add(showAllWords)

        listTV.add(textAllWords)
        listTV.add(apple)

        listEdt.add(edtWord1)
        listEdt.add(edtWord2)

        val style = Style()

        style.setBg(mainLL)
        style.setBtn(listBtn)
        style.setTV(listTV, listBtn, listEdt)
        style.setSheep(sheep)
    }

    private fun init() {
        mainLL = findViewById(R.id.mainLL)
        textFailedSymbol = findViewById(R.id.textViewHave)
        sheep = findViewById(R.id.imageViewSheep)
        imgInfo = findViewById(R.id.img_info)
        apple = findViewById(R.id.textAppleMainActivity)
        edtWord1 = findViewById(R.id.editEnglish)
        edtWord2 = findViewById(R.id.editRussian)
        textAllWords = findViewById(R.id.textAllWords)
        btnSave = findViewById(R.id.buttonwrite)
        btnEnd = findViewById(R.id.buttonend)
        showAllWords = findViewById(R.id.buttonallwords)
    }

    private fun setApple() {
        val workWithApple = WorkWithApple(getSharedPreferences(Keys.APP_NAME_FOR_SPREF, Context.MODE_PRIVATE))
        apple.text = workWithApple.getApple()
    }



    override fun onResume() {
        super.onResume()

        workWithItInCoroutinesInOnResume()
    }

    override fun onStop() {
        super.onStop()

        test()
    }

    private fun test() = CoroutineScope(Dispatchers.Default).launch {
        viewModel.getAllList().join()
        Log.d("test", viewModel.list.size.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this@WriteActivity, MainActivity::class.java))
    }
}