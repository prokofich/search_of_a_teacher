package com.example.searchofateacher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.example.searchofateacher.firestore.Firestore
import com.example.searchofateacher.model_for_get_data.CountUsers
import com.example.searchofateacher.model_for_get_data.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_registr.*
/*
Активити для регистрации с помощью паролю и почты
 */

class RegistrActivity : AppCompatActivity() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registr)

        //ПРИЛОЖЕНИЕ ВО ВЕСЬ ЭКРАН//////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ////////////////////////////////////////////////

        //ПРОВЕРКА ВВЕДЕННЫХ ДАННЫХ ПРИ РЕГИСТРАЦИИ/////////////////////////////////////////////////
        id_reg_button_registration.setOnClickListener {

            val name = id_reg_et_fname.text.toString() //ИМЯ
            val lastname = id_reg_et_lname.text.toString() //ФАМИЛИЯ
            val mail = id_reg_et_mail.text.toString() //ПОЧТА
            val password1 = id_reg_et_password1.text.toString() //ПАРОЛЬ
            val password2 = id_reg_et_password2.text.toString() //ПАРОЛЬ

            if (name == ""){
                Toast.makeText(this,"вы не ввели имя",Toast.LENGTH_SHORT).show()
            }else{
                if (lastname == ""){
                    Toast.makeText(this,"вы не ввели фамилию",Toast.LENGTH_SHORT).show()
                }else{
                    if (mail == ""){
                        Toast.makeText(this,"вы не ввели почту",Toast.LENGTH_SHORT).show()
                    }else{
                        if (password1 == ""){
                            Toast.makeText(this,"вы не ввели пароль",Toast.LENGTH_SHORT).show()
                        }else{
                            if (password2 == ""){
                                Toast.makeText(this,"вы не подтвердили пароль",Toast.LENGTH_SHORT).show()
                            }else{
                                if (password1!=password2){
                                    Toast.makeText(this,"пароли не совпадают",Toast.LENGTH_SHORT).show()
                                }else{

                                    signUp(mail,password1) //ФУНКЦИЯ РЕГИСТРАЦИИ ПО ПОЧТЕ И ПАРОЛЮ

                                }
                            }
                        }
                    }
                }
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////


    }

    //ФУНКЦИЯ РЕГИСТРАЦИИ ПОЛЬЗОВАТЕЛЯ//////////////////////////////////////////////////////////////
    private fun signUp(email:String, password:String){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val userId = firebaseUser.uid

                        // НУЛИ ОТВЕЧАЮТ ЗА КОЛИЧЕСТВО ЗАНЯТИЙ В КАЖДЫЙ ДЕНЬ НЕДЕЛИ/////////////////
                        val data_user = User(userId,
                            findViewById<EditText>(R.id.id_reg_et_fname).text.toString().trim{it <=' '},
                            findViewById<EditText>(R.id.id_reg_et_lname).text.toString().trim{it <=' '},
                            findViewById<EditText>(R.id.id_reg_et_mail).text.toString().trim{it <=' '},
                            findViewById<EditText>(R.id.id_reg_et_status).text.toString().trim{it <=' '},
                            0,0,0,0,0,
                            0,0
                        ) //////////////////////////////////////////////////////////////////////////

                        //ХЭШМАП ДЛЯ ОТПРАВКИ ЮЗЕРА В СПИСОК "INFO ALL USERS"/
                        val data_user_2 = hashMapOf(
                            "name" to id_reg_et_fname.text.toString(),
                            "lastname" to id_reg_et_lname.text.toString(),
                            "id" to userId,
                            "status" to id_reg_et_status.text.toString()
                        )/////////////////////////////////////////////////////

                        //ХЭШМАП КОЛИЧЕСТВА ОТПРАВЛЕННЫХ ЗАЯВОК/
                        val data_user_3 = hashMapOf(
                            "sent_count" to 0
                        )///////////////////////////////////////

                        //ХЭШМАП КОЛИЧЕСТВА ПОЛУЧЕННЫХ ЗАЯВОК/
                        val data_user_4 = hashMapOf(
                            "received_count" to 0
                        )/////////////////////////////////////

                        mFirestore.collection("USERS")
                            .document(userId)
                            .set(data_user, SetOptions.merge()) //ОТПРАВКА СВЕДЕНИЙ О ЮЗЕРЕ
                            .addOnSuccessListener {

                                mFirestore.collection("USERS")
                                    .document("Count_users")
                                    .get() //ПОЛУЧЕНИЕ КОЛИЧЕСТВА ЮЗЕРОВА
                                    .addOnSuccessListener { task->
                                        val count = task.toObject(CountUsers::class.java)

                                        mFirestore.collection("USERS").document("Count_users")
                                            .update("count_users",count!!.count_users+1) //ОБНОВЛЕНИЕ КОЛИЧЕСТВА ЮЗЕРОВ
                                        mFirestore.collection("INFO_ALL_USERS").document("${count!!.count_users+1}")
                                            .set(data_user_2) //ОТПРАВКА СВЕДЕНИЙ В СТАТУСНЫЙ СПИСОК
                                            .addOnSuccessListener {
                                                Toast.makeText(this,"данные добавлены", Toast.LENGTH_SHORT).show()
                                            }
                                        mFirestore.collection("USERS").document(userId)
                                            .update("number_in_tape",count!!.count_users+1) //ОТПРАВКА НОМЕРА ЮЗЕРА В ОБЩЕМ СПИСКЕ

                                        mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                                            .collection("Sent")
                                            .document("Sent_count")
                                            .set(data_user_3) // УСТАНОВКА КОЛИЧЕСТВА ОТПРАВЛЕННЫХ ЗАЯВОК

                                        mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                                            .collection("Received")
                                            .document("Received_count")
                                            .set(data_user_4) //УСТАНОВКА КОЛИЧЕСТА ПОЛУЧЕННЫХ ЗАЯВОК

                                    }

                                Toast.makeText(this,"регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java)) // ПЕРЕХОД НА ГЛАВНЫЙ ЭКРАН

                            }
                    }
                }
            )
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

}