package com.koleychik.maindicki.ui.activities

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.CheckWordToFailedSymbols
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.SheepGoing
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.coroutines.WorkWithApple
import com.koleychik.maindicki.models.WordModel
import com.koleychik.maindicki.ui.adapters.ShowWordsAdapter
import com.koleychik.maindicki.ui.viewHolders.ShowAllWordsViewHolder
import com.koleychik.maindicki.ui.viewModel.ShowAllWordsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.OptIn

class ShowAllWordsActivity : AppCompatActivity() {

    private lateinit var apple: TextView
    private lateinit var rv: RecyclerView
    private lateinit var btnClear: Button
    private lateinit var sheep: ImageView
    private lateinit var imgInfo: ImageView
    private lateinit var mainLL: View

    private lateinit var adapter: ShowWordsAdapter

    private val sheepGoing = SheepGoing(this@ShowAllWordsActivity, Keys.SHOW_ALL_FOR_SPREF, R.string.dialog_first_time_all_words_activity)

    private lateinit var viewModel : ShowAllWordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_words)

        viewModel = ViewModelProvider(this)[ShowAllWordsViewModel::class.java]

        init()

//        viewModel.makeItFirst()
        sheepGoing.checkIsItFirstTime()

//        makeLiveData()
        makeOnClickListener()
        setApple()
        setStyle()
//        makeSearch()
        makeAdapter()

        makeOnSwith()
    }

    private fun makeOnSwith() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as ShowAllWordsViewHolder).model.let {
                    viewModel.delete(id = it.id)
                    Log.d("swipe", "swipe left")
                }
            }
        }).attachToRecyclerView(rv)

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as ShowAllWordsViewHolder).model.let {
                    makeDialogChange(model = it)
                    Log.d("swipe", "swipe right")
                }
            }


        }).attachToRecyclerView(rv)
    }

    private fun makeDialogChange(model : WordModel) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_for_replace_word)

        val edtEng: EditText = dialog.findViewById(R.id.replace_first_word)
        val edtRus: EditText = dialog.findViewById(R.id.replace_russian_word)

        edtEng.setText(model.english)
        edtRus.setText(model.russian)

        val btn: Button = dialog.findViewById(R.id.buttonFinishReplaceWord)
        val redText: TextView = dialog.findViewById(R.id.red_text)

        val style = Style()
        val listBtn = mutableListOf<Button>()
        val listEdt = mutableListOf<EditText>()
        listEdt.add(edtEng)
        listEdt.add(edtRus)
        listBtn.add(btn)

//            style.setEdt(listEdt)
        style.setBtn(listBtn)
        style.setTV(mutableListOf(), listBtn, listEdt)

        dialog.show()

        btn.setOnClickListener {
            val checkWordToFailedSymbols = CheckWordToFailedSymbols()
            if (checkWordToFailedSymbols.checkFailedSymbols(edtEng.text.toString()) ||
                    checkWordToFailedSymbols.checkFailedSymbols(edtRus.text.toString())) {
                redText.text = getText(R.string.failedSymbol)
                redText.visibility = View.VISIBLE
            }
            else{
                dialog.dismiss()
                viewModel.updateById(id = model.id, english = edtEng.text.toString(), russian = edtRus.text.toString())
            }
        }
    }

    private fun makeAdapter() {

        val linearManager = LinearLayoutManager(this)
        adapter = ShowWordsAdapter()

        rv.layoutManager = linearManager

        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.allModels.collectLatest { adapter.submitData(it) }
        }

        rv.adapter = adapter
    }

    private fun setApple(){
        val workWithApple = WorkWithApple(getSharedPreferences(Keys.APP_NAME_FOR_SPREF, Context.MODE_PRIVATE))
        apple.text = workWithApple.getApple()
    }

    private fun makeOnClickListener(){
        val onClickListener = View.OnClickListener{
            when(it.id){
//                R.id.buttonClaerAll ->{viewModel.makeClearAll()}
                R.id.img_info -> sheepGoing.startSheep()
            }
        }

        btnClear.setOnClickListener(onClickListener)
        imgInfo.setOnClickListener(onClickListener)
    }

    private fun setStyle() {
        val listBtn = mutableListOf<Button>()
        val listTV = mutableListOf<TextView>()

        listBtn.add(btnClear)

        listTV.add(apple)

        val style = Style()

        style.setBg(mainLL)
        style.setBtn(listBtn)
        style.setTV(listTV, listBtn)
        style.setSheep(sheep)
    }

    private fun init() {
        apple = findViewById(R.id.textAppleMainActivity)
        rv = findViewById(R.id.rv_number)
        btnClear = findViewById(R.id.buttonClaerAll)
        sheep = findViewById(R.id.imageViewSheep)
        imgInfo = findViewById(R.id.img_info)
        mainLL = findViewById(R.id.mainLL)
    }
}