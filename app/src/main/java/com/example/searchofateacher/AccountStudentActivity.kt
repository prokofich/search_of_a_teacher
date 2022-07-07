package com.example.searchofateacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.searchofateacher.model_for_get_data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_account_student.*
/*
Профиль студента
 */
class AccountStudentActivity : AppCompatActivity() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_student)

        val id_student = intent.getStringExtra("key1") //ПОЛУЧЕНИЕ ID СТУДЕНТА ПРИ СТАРТЕ АКТИВИТИ

        //ПОКАЗ ДАННЫХ СТУДЕНТА НА СТРАНИЦЕ/////////////////////////////////////////////////////////
        mFirestore.collection("USERS").document("$id_student")
            .get()
            .addOnSuccessListener { task ->

                val data_student = task!!.toObject(User::class.java)

                id_accountstudent_tv_name.text = data_student!!.firstname+" "+data_student!!.fullname
                id_accountstudent_tv_text.text = data_student!!.text

            }
        ////////////////////////////////////////////////////////////////////////////////////////////

    }
}