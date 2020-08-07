package com.koleychik.maindicki.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.koleychik.maindicki.R
import com.koleychik.maindicki.additions.Keys
import com.koleychik.maindicki.additions.SheepGoing
import com.koleychik.maindicki.coroutines.WorkWithApple
import com.koleychik.maindicki.repositories.WordRepository

public class MainViewModel(application: Application ) : AndroidViewModel(application)  {

    private val context = application.applicationContext

    val repository = WordRepository(application)

    private val workWithApple = WorkWithApple(context.getSharedPreferences(Keys.APP_NAME_FOR_SPREF, Context.MODE_PRIVATE))

    private val sheepGoing = SheepGoing(context, Keys.MAIN_ACTIVITY_FOR_SPREF, R.string.dialog_first_time_main_activity)

//    val repository

//    class setStyle
//    onclickListenerForAll

//  sPref - getApple

    fun getApple() = workWithApple.getApple()

    fun checkIfFirstTime() {
        sheepGoing.checkIsItFirstTime()
    }

    fun onClickImgInfo(){
        sheepGoing.startSheep()
    }

    fun getSizeDb() = repository.getSizeDB()


}