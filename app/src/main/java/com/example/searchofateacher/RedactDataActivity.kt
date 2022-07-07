package com.example.searchofateacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.searchofateacher.firestore.Firestore
import com.example.searchofateacher.model_for_get_data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_redact_data.*
/*
Активити для изменения некоторых своих личных данных
 */

class RedactDataActivity : AppCompatActivity() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redact_data)

        mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
            .get()
            .addOnSuccessListener { task ->
                val datauser = task.toObject(User::class.java)
                //ПОКАЗ СТАРЫХ ДАННЫХ///////////////////////////////////////////////////////////////
                id_redactdata_tv_name.setText(datauser!!.firstname)
                id_redactdata_tv_fullname.setText(datauser!!.fullname)
                id_redactdata_tv_mobile.setText(datauser!!.mobile)
                ////////////////////////////////////////////////////////////////////////////////////
            }

        //ОБРАБОТКА СОХРАНЕНИЯ//////////////////////////////////////////////////////////////////////
        id_redactdata_button_save.setOnClickListener {

            val name = id_redactdata_tv_name.text.toString() //ИМЯ
            val fullname = id_redactdata_tv_fullname.text.toString() //ФАМИЛИЯ
            val mobile = id_redactdata_tv_mobile.text.toString() //НОМЕР ТЕЛЕФОНА
            val text = id_redactdata_tv_text.text.toString() //БИОГРАФИЯ

            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                .update("firstname",name,"fullname",fullname,"mobile",mobile,"text",text)
            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                .get()
                .addOnSuccessListener { task->
                    val datauser = task.toObject(User::class.java)

                    mFirestore.collection("INFO_ALL_USERS").document("${datauser!!.number_in_tape}")
                        .update("lastname",fullname,"name",name)
                }
            finish()
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

    }
}