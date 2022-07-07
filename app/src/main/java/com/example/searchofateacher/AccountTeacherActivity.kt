package com.example.searchofateacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.searchofateacher.firestore.Firestore
import com.example.searchofateacher.model_for_get_data.PredmetUser
import com.example.searchofateacher.model_for_get_data.ReceivedCount
import com.example.searchofateacher.model_for_get_data.SentCount
import com.example.searchofateacher.model_for_get_data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_account_teacher.*
/*
Профиль учителя с возможностью подать заявку на занятия
 */

class AccountTeacherActivity : AppCompatActivity() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_teacher)

        val id_teacher = intent.getStringExtra("key0")

        mFirestore.collection("USERS").document("$id_teacher")
            .get()
            .addOnSuccessListener { task->
                val user = task.toObject(User::class.java)
                id_accountteacher_tv_name.text = user!!.firstname + " " + user!!.fullname // ПОКАЗ ИМЕНИ УЧИТЕЛЯ

                //ПОЛУЧЕНИЕ И ПОКАЗ ПРЕДМЕТОВ УЧИТЕЛЯ///////////////////////////////////////////////
                for (i in 1..user!!.count_predmets){
                    mFirestore.collection("PREDMET_USERS").document("$id_teacher")
                        .collection("$i")
                        .document("$i")
                        .get()
                        .addOnCompleteListener { task2 ->

                            if (task2.isSuccessful){
                                if(task2.result!=null){
                                    if(task2.result.exists()){

                                        mFirestore.collection("PREDMET_USERS").document("$id_teacher")
                                            .collection("$i")
                                            .document("$i")
                                            .get()
                                            .addOnSuccessListener { task3->
                                                val teacher = task3.toObject(PredmetUser::class.java)
                                                addpredmet(teacher!!.predmet,teacher!!.price,teacher!!.text,id_teacher.toString(),i)
                                            }

                                    }
                                }
                            }


                        }
                }
                ////////////////////////////////////////////////////////////////////////////////////

            }

    }

    //ФУНКЦИЯ ДОБАВЛЕНИЯ ПРАЙС ЛИСТА РЕПЕТИТОРА/////////////////////////////////////////////////////
    private fun addpredmet(predmet:String,price:String,text:String,id_teach:String,number:Int){

        var blockView = View.inflate(this, R.layout.item_teacher_predmet, null)// СОЗДАНИЕ БЛОКА
        var blockpredmet = blockView.findViewById<TextView>(R.id.id_item_teacher_pr) // СТРОКА ПРЕДМЕТА В БЛОКЕ
        var blockprice = blockView.findViewById<TextView>(R.id.id_item_teacher_price) //СТРОКА ЦЕНЫ В БЛОКЕ
        var blocktext = blockView.findViewById<TextView>(R.id.id_item_teacher_text) //СТРОКА КОММЕНАТРИЯ К ПРЕДМЕТУ В БЛОКЕ
        var buttongo = blockView.findViewById<Button>(R.id.id_item_teacher_button) //КНОПКА ПОДАЧИ ЗАЯВКИ В БЛОКЕ

        blockpredmet.text = predmet //УСТАНОВКА НАЗВАНИЯ ПРЕДМЕТА
        blockprice.text = price //УСТАНОВКА ЦЕНЫ ПРЕДМЕТА
        blocktext.text = text //УСТАНОВКА КОММЕНТАРИЯ К ПРЕДМЕТУ

        //ПОДАЧА ЗАЯВКИ/////////////////////////////////////////////////////////////////////////////
        buttongo.setOnClickListener {

            //БЛОК ДАННЫХ О СТУДЕНТЕ,КОТОРЫЙ ОТПРАВИЛ ЗАЯВКУ//
            val data_for_request = hashMapOf(
                "id_student" to Firestore().getCurrentUserId(),
                "number_predmet" to number,
                "message" to ""
            )
            //////////////////////////////////////////////////

            if (buttongo.text == "хочу учиться!"){
                buttongo.text = "подтвердите участие"
            }else{

                mFirestore.collection("Request").document("$id_teach")
                    .collection("Received")
                    .document("Received_count")
                    .get()
                    .addOnSuccessListener { task ->
                        val Task = task!!.toObject(ReceivedCount::class.java)

                        mFirestore.collection("Request").document("$id_teach")
                            .collection("Received")
                            .document("${Task!!.received_count+1}")
                            .set(data_for_request)
                        mFirestore.collection("Request").document("$id_teach")
                            .collection("Received")
                            .document("Received_count")
                            .update("received_count",Task!!.received_count+1)

                        val data_for_request_2 = hashMapOf(
                            "id_teacher" to id_teach,
                            "number_predmet" to number,
                            "number_document" to Task!!.received_count+1
                        )

                        mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                            .collection("Sent")
                            .document("Sent_count")
                            .get()
                            .addOnSuccessListener { task2 ->
                                val Task2 = task2!!.toObject(SentCount::class.java)

                                mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                                    .collection("Sent")
                                    .document("${Task2!!.sent_count+1}")
                                    .set(data_for_request_2)
                                mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                                    .collection("Sent")
                                    .document("Sent_count")
                                    .update("sent_count",Task2!!.sent_count+1)
                            }
                    }
            }

        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        id_accountteacher_tape.addView(blockView)

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

}