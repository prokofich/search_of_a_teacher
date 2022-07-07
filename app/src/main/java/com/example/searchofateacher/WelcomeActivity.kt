package com.example.searchofateacher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

/*
Это активити создано для начального экрана
Оно будет работать 2 секунды,после чего произойдет переход в следующее активити
*/

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //ДВУХ СЕКУНДНОЕ ОЖИДАНИЕ+ПЕРЕХОД НА ВХОД///////////////////////////////////////////////////
        Handler().postDelayed({
            startActivity(Intent(this@WelcomeActivity , LoginActivity::class.java))
            finish()
        },2000)
        ////////////////////////////////////////////////////////////////////////////////////////////
    }
}