package com.example.searchofateacher.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.searchofateacher.R
import com.example.searchofateacher.SettingsActivity
import com.example.searchofateacher.firestore.Firestore
import com.example.searchofateacher.model_for_get_data.PredmetUser
import com.example.searchofateacher.model_for_get_data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_account.*
/*
Этот фрагмент яляется личным кабинетом пользователя(его профилем)
 */

class AccountFragment : Fragment() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ПЕРЕХОД В НАСТРОЙКИ///////////////////////////////////////////////////////////////////////
        id_account_button_settings.setOnClickListener {
            startActivity(Intent(context, SettingsActivity::class.java))
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //ПОКАЗ ПРАЙС-ЛИСТА/////////////////////////////////////////////////////////////////////////
        mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
            .get()
            .addOnSuccessListener { task ->
                val user = task.toObject(User::class.java)
                id_account_tv_name.text = user!!.firstname+" "+user!!.fullname
                id_account_tv_text.text = user!!.text

                for (i in 1..user!!.count_predmets){

                    mFirestore.collection("PREDMET_USERS").document(Firestore().getCurrentUserId())
                        .collection("${i}")
                        .document("${i}")
                        .get()
                        .addOnCompleteListener { task->

                            if(task.isSuccessful){
                                if(task.result!=null){
                                    if(task.result.exists()){

                                        mFirestore.collection("PREDMET_USERS").document(Firestore().getCurrentUserId())
                                            .collection("${i}")
                                            .document("${i}")
                                            .get()
                                            .addOnSuccessListener { task2 ->
                                                val pr = task2.toObject(PredmetUser::class.java)

                                                addpredmet2(pr!!.predmet,pr!!.price,pr!!.text,i)

                                            }

                                    }
                                }
                            }

                        }

                }

            }
        ////////////////////////////////////////////////////////////////////////////////////////////


        //ДОБАВКА НОВОЙ СТРОКИ О ПРЕДМЕТЕ///////////////////////////////////////////////////////////
        id_account_button_add.setOnClickListener {
            addpredmet()
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    //ФУНКЦИЯ ДОБАВЛЕНИЯ ПРАЙСА В БД////////////////////////////////////////////////////////////////
    private fun addpredmet(){
        var blockView = View.inflate(context, R.layout.item_account_predmet, null)//Создаём 1 block
        var blockpredmet = blockView.findViewById<EditText>(R.id.id_item_pr_pr)
        var blockprice = blockView.findViewById<EditText>(R.id.id_item_pr_price)
        var blocktext = blockView.findViewById<EditText>(R.id.id_item_pr_text)
        var buttonsave = blockView.findViewById<Button>(R.id.id_item_pr_button_save)
        var buttonredact = blockView.findViewById<Button>(R.id.id_item_pr_button_redact)

        buttonredact.isEnabled = false

        buttonsave.setOnClickListener {

            buttonredact.isEnabled = true

            val new_predmet = hashMapOf(
                "predmet" to blockpredmet.text.toString(),
                "price" to blockprice.text.toString(),
                "text" to blocktext.text.toString()
            )
            mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                .get()
                .addOnSuccessListener { task ->

                    val user = task.toObject(User::class.java)

                    mFirestore.collection("PREDMET_USERS").document(Firestore().getCurrentUserId()).collection("${user!!.count_predmets+1}")
                        .document("${user!!.count_predmets+1}")
                        .set(new_predmet)
                        .addOnSuccessListener {
                            Toast.makeText(context,"данные отправлены", Toast.LENGTH_SHORT).show()
                        }
                    mFirestore.collection("USERS").document(Firestore().getCurrentUserId())
                        .update("count_predmets",user!!.count_predmets+1)

                }
        }

        id_account_tape.addView(blockView)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //ФУНКЦИЯ ДОБАВЛЕНИЯ ПРАЙСА ИЗ БД///////////////////////////////////////////////////////////////
    private fun addpredmet2(predmet:String, price:String,text:String,number:Int){
        var blockView = View.inflate(context, R.layout.item_account_predmet2, null)//Создаём 1 block
        var blockpredmet = blockView.findViewById<EditText>(R.id.id_item_pr2_pr)
        var blockprice = blockView.findViewById<EditText>(R.id.id_item_pr2_price)
        var blocktext = blockView.findViewById<EditText>(R.id.id_item_pr2_text)
        var buttonredact = blockView.findViewById<Button>(R.id.id_item_pr2_button_redact)
        var buttondelete = blockView.findViewById<Button>(R.id.id_item_pr2_button_del)

        blockpredmet.setText(predmet)
        blockprice.setText(price)
        blocktext.setText(text)

        blockpredmet.isEnabled = false
        blockprice.isEnabled = false
        blocktext.isEnabled = false
        //ОБРАБОТКА РЕДАКТИРОВАНИЯ//////////////////////////////////////////////////////////////////
        buttonredact.setOnClickListener {

            if(buttonredact.text == "redact"){
                blockpredmet.isEnabled = true
                blockprice.isEnabled = true
                blocktext.isEnabled = true
                buttonredact.text = "save"
            }else{
                blockpredmet.isEnabled = false
                blockprice.isEnabled = false
                blocktext.isEnabled = false
                val pred = blockpredmet.text.toString()
                val pric = blockprice.text.toString()
                val tex = blocktext.text.toString()
                mFirestore.collection("PREDMET_USERS").document(Firestore().getCurrentUserId())
                    .collection("$number")
                    .document("$number")
                    .update("predmet",pred,"price",pric,"text",tex)
                buttonredact.text = "redact"
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //УДАЛЕНИЕ СВОИХ ПРЕДМЕТОВ//////////////////////////////////////////////////////////////////
        buttondelete.setOnClickListener {
            mFirestore.collection("PREDMET_USERS").document(Firestore().getCurrentUserId())
                .collection("$number")
                .document("$number")
                .delete()//удаление предмета
            id_account_tape.removeView(blockView)
        }
        ////////////////////////////////////////////////////////////////////////////////////////////


        id_account_tape.addView(blockView)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


}