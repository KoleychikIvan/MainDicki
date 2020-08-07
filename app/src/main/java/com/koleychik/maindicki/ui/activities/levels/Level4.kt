package com.koleychik.maindicki.ui.activities.levels

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.SheepGoing
import com.koleychik.maindicki.additions.Style
import com.koleychik.maindicki.animation.Anim_answer
import com.koleychik.maindicki.ui.activities.AfterLevelActivity
import com.koleychik.maindicki.ui.activities.ChooseLevelActivity
import com.koleychik.maindicki.ui.spinners.LanguageSpinnerAdapter
import com.koleychik.maindicki.ui.spinners.LanguageSpinnerModel
import com.koleychik.maindicki.ui.viewModel.Level4ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Level4 : AppCompatActivity() {

    private lateinit var viewModel: Level4ViewModel

    private lateinit var sheep: ImageView
    private lateinit var imgInfo: ImageView

    private lateinit var word1: TextView
    private lateinit var word2: TextView
    private lateinit var skip: TextView
    private lateinit var apple: TextView

    private lateinit var microphone: View
    private lateinit var mainLL: View
    private lateinit var layoutYes: View
    private lateinit var layoutNo: View

    private lateinit var spinner: Spinner

    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
    private lateinit var speechRecognizerIntent: Intent

    private var language = "eng"

    private val sheepGoing = SheepGoing(this, Keys.LEVEL_4_FOR_SPREF, R.string.dialog_first_time_exam)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level4)

        viewModel = ViewModelProvider(this)[Level4ViewModel::class.java]
        viewModel.makeItFirst()

        sheepGoing.checkIsItFirstTime()

        init()
        subscribe()
        setStyle()
        checkPermission()
        makeOnClick()
        makeSpinner()
        makeOnClickToMicroPhone()
        makeSpinnerOnClick()
        viewModel.getNewWord()
    }

    private fun makeSpinnerOnClick() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                language = "en"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> language = "en"
                    1 -> language = "de"
                    2 -> language = "pl"
                    3 -> language = "es"
                    4 -> language = "fr"
                }
                speechRecognizerIntent = makeSpeechIntent(language)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun makeOnClickToMicroPhone() = CoroutineScope(Dispatchers.Main).launch {
        microphone.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    speechRecognizer.startListening(speechRecognizerIntent)
                    microphone.setBackgroundResource(R.drawable.microphone_on_touch)
                    Log.d("level4", "down")
                }
                MotionEvent.ACTION_UP -> {
                    speechRecognizer.stopListening()
                    microphone.setBackgroundResource(R.drawable.microphone)
                    Log.d("level4", "up")
//                    makeCheck()
                }
            }
            true
//            v?.onTouchEvent(event) ?: true
        }
    }

    private fun makeCheck(answer : String) {
        Log.d("level4", answer)

        when (viewModel.makeCheck(answer)) {
            -1 -> {
                val anim = Anim_answer(this, layoutNo, viewModel.getAnswer())
                anim.AnimStart()
            }
            0 -> {
                val anim = Anim_answer(this, layoutYes, viewModel.getAnswer())
                anim.AnimStart()
                viewModel.getNewWord()
            }
            1 -> {
                val intent = Intent(this@Level4, AfterLevelActivity::class.java)
                intent.putExtra(Keys.BEFORE_LEVEL, 4)
                intent.putExtra(Keys.GET_APPLE, viewModel.numberApple.value)
                startActivity(intent)
            }
        }
    }

    private fun makeSpinner() {
        spinner = findViewById(R.id.spinnerLang)
        spinner.adapter = LanguageSpinnerAdapter(context = this, modelList = makeSpinnerList())
        spinner.setSelection(0)
    }

    private fun makeOnClick() {
        val onClickListener = View.OnClickListener {
            when (it.id) {
                R.id.img_info -> sheepGoing.startSheep()
                R.id.exam_next -> if(viewModel.makeSkip() == 1) makeCheck("")
            }
        }

        imgInfo.setOnClickListener(onClickListener)
        skip.setOnClickListener(onClickListener)
    }

    private fun subscribe() {
        viewModel.wordRus.observe(this, Observer {
            word1.text = it
        })
        viewModel.wordEng.observe(this, Observer {
            word2.visibility = View.GONE
            word2.text = it
        })
        viewModel.numberApple.observe(this, Observer {
            apple.text = it.toString()
        })
    }

    private fun checkPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {}
                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        startActivity(Intent(this@Level4, ChooseLevelActivity::class.java))
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    }
                }).check()
    }

    private fun makeSpinnerList(): MutableList<LanguageSpinnerModel> {
        return mutableListOf(LanguageSpinnerModel(R.string.eng),
                LanguageSpinnerModel(R.string.ger),
                LanguageSpinnerModel(R.string.pln),
                LanguageSpinnerModel(R.string.esp),
                LanguageSpinnerModel(R.string.fra))
    }

    private fun setStyle() {
        val listBtn = mutableListOf<View>()
        val listTv = mutableListOf<TextView>()

        listBtn.add(spinner)

        val style = Style()
        style.setBg(mainLL)
        style.setBtn(listBtn)
        style.setTV(listTv, mutableListOf())
        style.setSheep(sheep)
    }

    private fun init() {
        sheep = findViewById(R.id.imageViewSheep)
        imgInfo = findViewById(R.id.img_info)

        word1 = findViewById(R.id.exam_rus)
        word2 = findViewById(R.id.exam_eng)
        skip = findViewById(R.id.exam_next)
        apple = findViewById(R.id.textAppleMainActivity)

        mainLL = findViewById(R.id.mainLL)
        microphone = findViewById(R.id.microphone)
        layoutYes = findViewById(R.id.yes)
        layoutNo = findViewById(R.id.no)

        spinner = findViewById(R.id.spinnerLang)
    }

    override fun onDestroy() {
        super.onDestroy()

        speechRecognizer.cancel()
    }


    //    setOnTouchListenerMicrophone()
    fun makeSpeechIntent(language: String): Intent {
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                Log.d("test_level4", error.toString())
            }

            override fun onResults(results: Bundle) {
                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    makeCheck(matches[0])
                }
                matches?.get(0)?.let { Log.d("HelpLevel4", it) }
            }

            override fun onPartialResults(partialResults: Bundle) {}
            override fun onEvent(eventType: Int, params: Bundle) {}
        })
        return speechRecognizerIntent
    }
}