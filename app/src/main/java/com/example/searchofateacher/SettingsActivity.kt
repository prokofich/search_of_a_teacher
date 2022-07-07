package com.example.searchofateacher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
/*
Это активити настроек
Отсюда можно выйти из аккаунта или перейти к изменению своих данных
 */

class SettingsActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mAuth = FirebaseAuth.getInstance()

        //ВЫХОД ИЗ ПРИЛОЖЕНИЯ///////////////////////////////////////////////////////////////////////
        id_settings_exit.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //ПЕРЕХОД К ИЗМЕНЕНИЮ ДАННЫХ////////////////////////////////////////////////////////////////
        id_settings_redactdata.setOnClickListener {
            startActivity(Intent(this,RedactDataActivity::class.java))
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

    }
}