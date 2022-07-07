package com.example.searchofateacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.searchofateacher.firestore.Firestore
import com.example.searchofateacher.model_for_get_data.TimePredmet
import com.example.searchofateacher.model_for_get_data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_day_of_the_week.*

/*
Активити для показа своего расписания в конкретный день недели
есть возможность изменения или добавления нового расписания
 */

class DayOfTheWeekActivity : AppCompatActivity() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_of_the_week)

        val flag = intent.getStringExtra("1").toString() //flag хранит в себе день недели

        id_dayweek_button_add.setOnClickListener {
            addtimepredmet(flag) //ВЫЗОВ ФУНКЦИИ ДОБАВЛЕНИЯ ПРЕДМЕТА
        }

        mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
            .get()
            .addOnSuccessListener { task ->
                val user = task.toObject(User::class.java)
                var count = -1

                when (flag) {
                    "monday" -> count = user!!.count_in_monday
                    "tuesday" -> count = user!!.count_in_tuesday
                    "wednesday" -> count = user!!.count_in_wednesday
                    "thursday" -> count = user!!.count_in_thursday
                    "friday" -> count = user!!.count_in_friday
                    "saturday" -> count = user!!.count_in_saturday
                    "sunday" -> count = user!!.count_in_sunday
                }

                for (i in 1..count) {

                    mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                        .collection("${flag}")
                        .document("$i")
                        .get()
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                if (task.result != null) {
                                    if (task.result.exists()) {

                                        mFirestore.collection("TIMETABLE")
                                            .document(Firestore().getCurrentUserId())
                                            .collection("${flag}")
                                            .document("$i")
                                            .get()
                                            .addOnSuccessListener { task2 ->
                                                val time = task2.toObject(TimePredmet::class.java)

                                                addtimepredmet2(time!!.time,time!!.predmet,i,flag)

                                            }
                                    }
                                }
                            }
                        }
                }
            }

    }

    //ФУНКЦИЯ ДОБАВЛЕНИЯ РАСПИСАНИЯ ////////////////////////////////////////////////////////////////
    private fun addtimepredmet(Flag:String){

        var blockView = View.inflate(this, R.layout.item_dayweek_time, null)//Создаём 1 block
        var blocktime = blockView.findViewById<EditText>(R.id.id_item_dayweek_time)
        var blockpredmet = blockView.findViewById<EditText>(R.id.id_item_dayweek_predmet)
        var blockbuttonsave = blockView.findViewById<Button>(R.id.id_item_dayweek_button_save)

        blockbuttonsave.setOnClickListener {

            val new_timetable = hashMapOf(
                "time" to blocktime.text.toString(),
                "predmet" to blockpredmet.text.toString()
            )

            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                .get()
                .addOnSuccessListener { task->
                    val user = task.toObject(User::class.java)
                    when(Flag){
                        "monday" -> {
                            mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                                .collection("monday").document("${user!!.count_in_monday + 1}")
                                .set(new_timetable)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "добавлено в расписание",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                                .update("count_in_monday",user!!.count_in_monday+1)
                        }
                        "tuesday" -> {
                            mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                                .collection("tuesday").document("${user!!.count_in_tuesday + 1}")
                                .set(new_timetable)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "добавлено в расписание",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                                .update("count_in_tuesday",user!!.count_in_tuesday+1)
                        }
                        "wednesday" -> {
                            mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                                .collection("wednesday").document("${user!!.count_in_wednesday + 1}")
                                .set(new_timetable)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "добавлено в расписание",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                                .update("count_in_wednesday",user!!.count_in_wednesday+1)
                        }
                        "thursday" -> {
                            mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                                .collection("thursday").document("${user!!.count_in_thursday + 1}")
                                .set(new_timetable)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "добавлено в расписание",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                                .update("count_in_thursday",user!!.count_in_thursday+1)
                        }
                        "friday" -> {
                            mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                                .collection("friday").document("${user!!.count_in_friday + 1}")
                                .set(new_timetable)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "добавлено в расписание",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                                .update("count_in_friday",user!!.count_in_friday+1)
                        }
                        "saturday" -> {
                            mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                                .collection("saturday").document("${user!!.count_in_saturday + 1}")
                                .set(new_timetable)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "добавлено в расписание",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                                .update("count_in_saturday",user!!.count_in_saturday+1)
                        }
                        "sunday" -> {
                            mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                                .collection("sunday").document("${user!!.count_in_sunday + 1}")
                                .set(new_timetable)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "добавлено в расписание",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                                .update("count_in_sunday",user!!.count_in_sunday+1)
                        }
                    }
                }
        }

        id_dayweek_tape.addView(blockView)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //ФУНКЦИЯ ПОКАЗА РАСПИСАНИЯ/////////////////////////////////////////////////////////////////////
    private fun addtimepredmet2(time:String,predmet:String,number:Int,day:String){

        var blockView = View.inflate(this, R.layout.item_dayweek_time2, null)
        var blocktime = blockView.findViewById<EditText>(R.id.id_item_dayweek_time2)
        var blockpredmet = blockView.findViewById<EditText>(R.id.id_item_dayweek_predmet2)
        var blockbuttonredact = blockView.findViewById<Button>(R.id.id_item_dayweek_button_redact2)
        var blockbuttondelete = blockView.findViewById<Button>(R.id.id_item_dayweek_button_del2)

        blocktime.setText(time)
        blockpredmet.setText(predmet)

        blocktime.isEnabled = false
        blockpredmet.isEnabled = false
        //ОБРАБОТКА РЕДАКТИРОВАНИ///////////////////////////////////////////////////////////////////
        blockbuttonredact.setOnClickListener {
            if(blockbuttonredact.text == "redact"){
                blocktime.isEnabled = true
                blockpredmet.isEnabled = true
                blockbuttonredact.text = "save"
            }else{
                blocktime.isEnabled = false
                blockpredmet.isEnabled = false
                val pred = blockpredmet.text.toString()
                val tim = blocktime.text.toString()
                mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                    .collection(day).document("$number").update("predmet",pred,"time",tim)
                blockbuttonredact.setText("redact")
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //УДАЛЕНИЕ РАПИСАНИЯ////////////////////////////////////////////////////////////////////////
        blockbuttondelete.setOnClickListener {
            mFirestore.collection("TIMETABLE").document(Firestore().getCurrentUserId())
                .collection(day)
                .document("$number")
                .delete()//удаление расписания
            id_dayweek_tape.removeView(blockView)
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        id_dayweek_tape.addView(blockView)

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

}