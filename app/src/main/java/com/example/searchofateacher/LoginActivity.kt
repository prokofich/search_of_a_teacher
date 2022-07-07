package com.example.searchofateacher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
/*
Активити для входа через пароль и логин
есть возможность безлогинового входа,если юзер не выходил из аккаунта
 */

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //БЕЗЛОГИНОВЫЙ ВХОД В ПРИЛОЖЕНИЕ/////////////////////////////////////
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
        }
        /////////////////////////////////////////////////////////////////////

        //ПРИЛОЖЕНИЕ ВО ВЕСЬ ЭКРАН///////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        /////////////////////////////////////////////////

        //ПЕРЕХОД НА РЕГИСТРАЦИЮ////////////////////////////////////////////////
        id_login_create_account.setOnClickListener {
            startActivity(Intent(this,RegistrActivity::class.java))
        }
        ////////////////////////////////////////////////////////////////////////

        //ОБРАБОТКА ВХОДА ЗАРЕГИСТРИРОВАННОГО ПОЛЬЗОВАТЕЛЯ///
        id_login_input.setOnClickListener {
            InputUser()
        }
        ////////////////////////////////////////////////////

    }

    //ФУНКЦИЯ ВХОДА ЗАРЕГИСТРИРОВАННОГО ПОЛЬЗОВАТЕЛЯ////////////////////////////////////////////////
    private fun InputUser(){
        val email = id_login_mail.text.toString()
        val password = id_login_password.text.toString()

        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                }else{
                    Toast.makeText(this, "ошибка:"+task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}