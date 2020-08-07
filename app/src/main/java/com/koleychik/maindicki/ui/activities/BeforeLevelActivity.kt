package com.koleychik.maindicki.ui.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.SheepGoing
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.coroutines.WorkWithApple
import com.koleychik.maindicki.ui.activities.levels.Level1
import com.koleychik.maindicki.ui.activities.levels.Level2
import com.koleychik.maindicki.ui.activities.levels.Level3
import com.koleychik.maindicki.ui.activities.levels.Level4
import com.koleychik.maindicki.ui.adapters.BeforeLevelAdapter
import com.koleychik.maindicki.ui.viewModel.BeforeLevelViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BeforeLevelActivity : AppCompatActivity(){

    private lateinit var viewModel : BeforeLevelViewModel

    private lateinit var mainLL : View

    private lateinit var btnStop : Button

    private lateinit var apple : TextView

    private lateinit var imgInfo : ImageView
    private lateinit var sheep : ImageView

    private lateinit var rv: RecyclerView

    private lateinit var adapter : BeforeLevelAdapter

    private val sheepGoing = SheepGoing(this@BeforeLevelActivity, Keys.BEFORE_LEVEL_FOR_SPREF, R.string.dialog_first_time_before_before_level_activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_before_level)

        viewModel = ViewModelProvider(this)[BeforeLevelViewModel::class.java]

        sheepGoing.checkIsItFirstTime()

        viewModel.selectAll(false)

        init()
        setStyle()
        makeOnClickListener()
        setApple()
        makeDialogBeforeLevel()
        makeAdapter()
    }

    private fun makeAdapter() {
        val linearManager = LinearLayoutManager(this)
        adapter = BeforeLevelAdapter()

        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.allModels.collectLatest { adapter.submitData(it) }
        }

        rv.layoutManager = linearManager
        rv.adapter = adapter
    }


    private fun makeDialogBeforeLevel(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_before_before_level)

        val btnSameWords : Button = dialog.findViewById(R.id.button_N_number_words)
        val btnAllWords : Button = dialog.findViewById(R.id.button_dialog_all_words)
        val btnChooseAlone : Button = dialog.findViewById(R.id.button_dialog_choose_yourself)

        val style = Style()
        val listBtn = mutableListOf<Button>()
        listBtn.add(btnAllWords)
        listBtn.add(btnChooseAlone)
        listBtn.add(btnSameWords)

        style.setBtn(listBtn)
        style.setTV(mutableListOf(), listBtn)

        dialog.show()

        val onClickListener = View.OnClickListener{
            when(it.id){
                R.id.button_N_number_words -> {
                    makeDialogSelectSameWords()
                    dialog.cancel()
                }
                R.id.button_dialog_all_words -> {
                    viewModel.selectAll(true)
                    tryGetIntent()
                    dialog.cancel()
                }
                R.id.button_dialog_choose_yourself -> dialog.cancel()
            }
        }
        btnAllWords.setOnClickListener(onClickListener)
        btnSameWords.setOnClickListener(onClickListener)
        btnChooseAlone.setOnClickListener(onClickListener)
    }

    private fun makeDialogSelectSameWords(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.second_dialog_before_before_level)

        val editText : EditText = dialog.findViewById(R.id.ED_second_dialog)
        val btnContinue : Button = dialog.findViewById(R.id.button_secondDialog)

        val style = Style()
        val listBtn = mutableListOf<Button>()
        val listEdt = mutableListOf<EditText>()
        style.setBtn(listBtn)
        style.setTV(mutableListOf(), listBtn, listEdt)

        btnContinue.setOnClickListener {
            if (dialogCheckNumber(editText.text.toString().toInt(), dialog)) {
                viewModel.selectSameWords(editText.text.toString().toInt())
                dialog.cancel()
                tryGetIntent()
            }
        }

        dialog.show()
    }

    private fun dialogCheckNumber(number : Int, dialog: Dialog) : Boolean{
        if (number < 3){
            val redText : TextView = dialog.findViewById(R.id.redText)
            redText.visibility = View.VISIBLE
            redText.setText(R.string.needChoosMoreThen2Words)
            return false
        }
        if (number > viewModel.getCount()){
            val redText : TextView = dialog.findViewById(R.id.redText)
            redText.visibility = View.VISIBLE
            redText.setText(R.string.tooMachNumber)
            return false
        }
        return true
    }

    private fun setApple(){
        val workWithApple = WorkWithApple(getSharedPreferences(Keys.APP_NAME_FOR_SPREF, Context.MODE_PRIVATE))
        apple.text = workWithApple.getApple()
    }

    private fun makeOnClickListener(){
        val onClickListener = View.OnClickListener{
            when(it.id){
                R.id.img_info -> sheepGoing.startSheep()
                R.id.button -> {
                    val list = adapter.whenAppDestroy()
                    if (list.size < 3) {
                        Toast.makeText(this, getString(R.string.needChoosMoreThen2Words), Toast.LENGTH_LONG).show()
                    } else {
                        for (i in list) {
                            viewModel.setCheck(i.id, true)
                        }
                        tryGetIntent()
                    }
                }
            }
        }
        btnStop.setOnClickListener(onClickListener)
        imgInfo.setOnClickListener(onClickListener)
    }

    private fun tryGetIntent(){
        when(intent.extras?.getInt(Keys.BEFORE_LEVEL)){
            1 -> startActivity(Intent(this@BeforeLevelActivity, Level1::class.java))
            2 -> startActivity(Intent(this@BeforeLevelActivity, Level2::class.java))
            3 -> startActivity(Intent(this@BeforeLevelActivity, Level3::class.java))
            4 -> startActivity(Intent(this@BeforeLevelActivity, Level4::class.java))
        }
    }

    private fun setStyle(){
        val listBtn = mutableListOf<Button>()
        val listTV = mutableListOf<TextView>()

        listBtn.add(btnStop)
        listTV.add(apple)

        val style = Style()
        style.setTV(listTV, listBtn)
        style.setBtn(listBtn)
        style.setBg(mainLL)
        style.setSheep(sheep)
    }

    private fun init(){
        btnStop = findViewById(R.id.button)

        mainLL = findViewById(R.id.mainLL)

        imgInfo = findViewById(R.id.img_info)
        sheep = findViewById(R.id.imageViewSheep)

        apple = findViewById(R.id.textAppleMainActivity)

        rv = findViewById(R.id.rv)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}