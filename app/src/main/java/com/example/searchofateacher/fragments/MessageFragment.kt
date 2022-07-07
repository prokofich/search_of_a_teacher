package com.example.searchofateacher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.searchofateacher.R
import com.example.searchofateacher.firestore.Firestore
import com.example.searchofateacher.model_for_get_data.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_message.*
/*
Этот фрагмент является окном для отображения и управлением полученных или отправленных заявок об обучении
 */

class MessageFragment : Fragment() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ОБРАБОТКА ПОКАЗА ЗАЯВОК///////////////////////////////////////////////////////////////////
        mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
            .get()
            .addOnSuccessListener {  task ->
                val Task = task!!.toObject(User::class.java)

                //ОБРАБОТКА ДЛЯ УЧЕНИКА/////////////////////////////////////////////////////////////
                if(Task!!.status == "ученик"){
                    id_message_request_tv.text = "отправленные заявки"

                    mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                        .collection("Sent")
                        .document("Sent_count")
                        .get()
                        .addOnSuccessListener { task2 ->
                            val Task2 = task2!!.toObject(SentCount::class.java)

                            for (i in 1..Task2!!.sent_count){

                                mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                                    .collection("Sent")
                                    .document("$i")
                                    .get()
                                    .addOnCompleteListener { task3 ->
                                        if(task3.isSuccessful){
                                            if(task3.result!=null){
                                                if(task3.result.exists()){

                                                    mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                                                        .collection("Sent")
                                                        .document("$i")
                                                        .get()
                                                        .addOnSuccessListener { task4 ->
                                                            val Task4 = task4!!.toObject(Sent::class.java)

                                                            mFirestore.collection("USERS").document(Task4!!.id_teacher)
                                                                .get()
                                                                .addOnSuccessListener { task5 ->
                                                                    val Task5 = task5!!.toObject(User::class.java)

                                                                    mFirestore.collection("PREDMET_USERS").document(Task4!!.id_teacher)
                                                                        .collection("${Task4!!.number_predmet}")
                                                                        .document("${Task4!!.number_predmet}")
                                                                        .get()
                                                                        .addOnSuccessListener { task6 ->
                                                                            val Task6 = task6!!.toObject(PredmetUser::class.java)

                                                                            addsentrequest(Task5!!.firstname+" "+Task5!!.fullname,Task6!!.predmet,Task6!!.price,"ожидание",Task4!!.id_teacher,Task4!!.number_document,i)

                                                                        }

                                                                }

                                                        }

                                                }
                                            }
                                        }

                                    }

                            }

                        }

                    //ОБРАБОТКА ДЛЯ РЕПЕТИТОРА//////////////////////////////////////////////////////////
                }else{

                    id_message_request_tv.text = "полученные заявки"

                    mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                        .collection("Received")
                        .document("Received_count")
                        .get()
                        .addOnSuccessListener { task2 ->
                            val Task2 = task2!!.toObject(ReceivedCount::class.java)

                            for (i in 1..Task2!!.received_count){

                                mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                                    .collection("Received")
                                    .document("$i")
                                    .get()
                                    .addOnCompleteListener { task3 ->
                                        if(task3.isSuccessful){
                                            if(task3.result!=null){
                                                if(task3.result.exists()){

                                                    mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                                                        .collection("Received")
                                                        .document("$i")
                                                        .get()
                                                        .addOnSuccessListener { task4 ->
                                                            val Task4 = task4!!.toObject(Received::class.java)

                                                            mFirestore.collection("USERS").document(Task4!!.id_student)
                                                                .get()
                                                                .addOnSuccessListener { task5 ->
                                                                    val Task5 = task5!!.toObject(User::class.java)

                                                                    mFirestore.collection("PREDMET_USERS").document(Firestore().getCurrentUserId())
                                                                        .collection("${Task4!!.number_predmet}")
                                                                        .document("${Task4!!.number_predmet}")
                                                                        .get()
                                                                        .addOnSuccessListener { task6 ->
                                                                            val Task6 = task6!!.toObject(PredmetUser::class.java)

                                                                            if (Task4.message != "отказ"){

                                                                                addreceivedrequest(Task5!!.firstname+" "+Task5!!.fullname,Task6!!.predmet,Task6!!.price,Task4!!.id_student,i)

                                                                            }

                                                                        }
                                                                }
                                                        }
                                                }
                                            }
                                        }

                                    }

                            }

                        }

                }

            }

        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    //ФУНКЦИЯ ДОБАВЛЕНИЯ ОТПРАВЛЕННЫХ ЗАЯВОК////////////////////////////////////////////////////////
    private fun addsentrequest(name:String,predmet:String, price:String,status:String,id_teach:String,doc:Int,my_doc:Int){

        var blockView = View.inflate(context, R.layout.item_message_request, null)
        var blockpredmet = blockView.findViewById<TextView>(R.id.id_item_message_request_pr)
        var blockprice = blockView.findViewById<TextView>(R.id.id_item_message_request_price)
        var blockstatus = blockView.findViewById<TextView>(R.id.id_item_message_request_status)
        var blockname = blockView.findViewById<TextView>(R.id.id_item_message_request_name)
        var blockdelete = blockView.findViewById<Button>(R.id.id_item_message_request_del)
        var blockmobile = blockView.findViewById<TextView>(R.id.id_item_message_request_mobile)

        blockname.text = name
        blockpredmet.text = predmet
        blockprice.text = price
        blockstatus.text = status

        //ПРОВЕРКА СТАТУСА ЗАЯВКИ///////////////////////////////////////////////////////////////////
        mFirestore.collection("Request").document("${id_teach}").collection("Received")
            .document("${doc}")
            .addSnapshotListener { value, error ->

                if (value!=null && value.exists()) {

                    var sms = value!!.toObject(Received::class.java)

                    if (sms!!.message == "отказ"){
                        blockstatus.text = "отказано"
                    }

                    if (sms!!.message == "согласие"){
                        blockstatus.text = "принято"
                        mFirestore.collection("USERS").document("${id_teach}")
                            .get()
                            .addOnSuccessListener { task ->
                                val user = task!!.toObject(User::class.java)
                                blockmobile.text ="звоните по номеру ${user!!.mobile}"
                            }
                    }
                }
            }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //ОБРАБОТКА УДАЛЕНИЯ ЗАЯВКИ/////////////////////////////////////////////////////////////////
        blockdelete.setOnClickListener {

            mFirestore.collection("Request").document(id_teach)
                .collection("Received")
                .document("$doc")
                .delete()
            mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                .collection("Sent")
                .document("$my_doc")
                .delete()
            id_message_request_tape.removeView(blockView)
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        id_message_request_tape.addView(blockView)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //ФУНКЦИЯ ДОБАВЛЕНИЯ ПОЛУЧЕННЫХ ЗАЯВОК////////////////////////////////////////////////////////
    private fun addreceivedrequest(name:String,predmet:String, price:String,id_stud:String,numberdoc:Int){

        var blockView = View.inflate(context, R.layout.item_message_request_received, null)
        var blockpredmet = blockView.findViewById<TextView>(R.id.id_item_message_request_received_pr)
        var blockprice = blockView.findViewById<TextView>(R.id.id_item_message_request_received_price)
        var blockname = blockView.findViewById<TextView>(R.id.id_item_message_request_received_name)
        var blockyes = blockView.findViewById<Button>(R.id.id_item_message_request_received_button_yes)
        var blockno = blockView.findViewById<Button>(R.id.id_item_message_request_received_button_no)


        blockname.text = name
        blockpredmet.text = predmet
        blockprice.text = price

        //ОБРАБОТКА ОТКАЗА ОТ ПРЕДЛОЖЕНИЯ///////////////////////////////////////////////////////////
        blockno.setOnClickListener {

            mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                .collection("Received").document("$numberdoc").update("message","отказ")
            id_message_request_tape.removeView(blockView)

        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //ОБРАБОТКА СОГЛАСИЯ НА ПРЕДЛОЖЕНИЕ/////////////////////////////////////////////////////////
        blockyes.setOnClickListener {

            mFirestore.collection("Request").document(Firestore().getCurrentUserId())
                .collection("Received").document("$numberdoc").update("message","согласие")

        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        id_message_request_tape.addView(blockView)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


}