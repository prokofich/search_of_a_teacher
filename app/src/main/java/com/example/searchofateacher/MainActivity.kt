package com.example.searchofateacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.searchofateacher.fragments.AccountFragment
import com.example.searchofateacher.fragments.MessageFragment
import com.example.searchofateacher.fragments.SearchFragment
import com.example.searchofateacher.fragments.TimetableFragment
import kotlinx.android.synthetic.main.activity_main.*
/*
Активити содержит в себе меню и окно для фрагментов(поиск,заявки,профиль и расписание занятий)
 */

class MainActivity : AppCompatActivity() {

    private val accountfragment = AccountFragment()
    private val messagefragment = MessageFragment()
    private val searchfragment = SearchFragment()
    private val timetablefragment = TimetableFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ПРИЛОЖЕНИЕ ВО ВЕСЬ ЭКРАН/////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ///////////////////////////////////////////////

        //ОБРАБОТКА НАЖАТИЯ КНОПОК В МЕНЮ+ПОКАЗ ФРАГМЕНТОВ//////////////////////////////////////////
        replaceFragment(searchfragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.id_menu_account ->replaceFragment(accountfragment)
                R.id.id_menu_message ->replaceFragment(messagefragment)
                R.id.id_menu_search ->replaceFragment(searchfragment)
                R.id.id_menu_timetable ->replaceFragment(timetablefragment)
            }
            true
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    //ФУНКЦИЯ ПОКАЗА ФРАГМЕНТА//////////////////////////////////////////////////////////////////////
    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}