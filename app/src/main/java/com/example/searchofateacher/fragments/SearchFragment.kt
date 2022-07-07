package com.example.searchofateacher.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.searchofateacher.AccountStudentActivity
import com.example.searchofateacher.AccountTeacherActivity
import com.example.searchofateacher.R
import com.example.searchofateacher.model_for_get_data.CountUsers
import com.example.searchofateacher.model_for_get_data.InfoUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_search.*
/*
Фрагмент для поиска репетиторов и студентов
 */

class SearchFragment : Fragment() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ПОКАЗ ВСЕХ РЕПЕТИТОРОВ В ЛЕНТЕ ПОИСКА/////////////////////////////////////////////////////
        id_search_button_teacher.setOnClickListener {

            id_search_tape_users.removeAllViews()
            mFirestore.collection("USERS").document("Count_users")
                .get()
                .addOnSuccessListener { task ->

                    val count = task.toObject(CountUsers::class.java)

                    for (i in 1..count!!.count_users) {
                        mFirestore.collection("INFO_ALL_USERS").document("$i")
                            .get()
                            .addOnSuccessListener { task2 ->
                                val info_user = task2.toObject(InfoUser::class.java)
                                if (info_user!!.status == "репетитор") {
                                    adduser(info_user!!.id, info_user!!.name, info_user!!.lastname,i,0)
                                }
                            }
                    }
                }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////


        //ПОКАЗ ВСЕХ УЧЕНИКОВ В ЛЕНТЕ ПОИСКА////////////////////////////////////////////////////////
        id_search_button_uchenik.setOnClickListener {

            id_search_tape_users.removeAllViews()

            mFirestore.collection("USERS").document("Count_users")
                .get()
                .addOnSuccessListener { task ->

                    val count = task.toObject(CountUsers::class.java)

                    for (i in 1..count!!.count_users) {
                        mFirestore.collection("INFO_ALL_USERS").document("${i}")
                            .get()
                            .addOnSuccessListener { task2 ->
                                val info_user = task2.toObject(InfoUser::class.java)
                                if (info_user!!.status == "ученик") {
                                    adduser(info_user!!.id, info_user!!.name, info_user!!.lastname,i,1)
                                }
                            }
                    }
                }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

    }


    //ФУНКЦИЯ ДОБАВЛЕНИЯ ПОЛЬЗОВАТЕЛЯ В ЛЕНТУ ПОИСКА////////////////////////////////////////////////
    private fun adduser(id:String,name:String,lastname:String,number:Int,flag:Int){

        var blockView = View.inflate(context, R.layout.item_user_for_search, null)//Создаём 1 block
        var blockname = blockView.findViewById<TextView>(R.id.id_item_searc_tv_name)
        var blockstatus = blockView.findViewById<TextView>(R.id.id_item_search_tv_status)

        blockname.text = name + " " + lastname
        blockstatus.text = "в сети"

        //ПЕРЕХОД В АККАУНТ ВЫБРАННОГО ЮЗЕРА////////////////////////////////////////////////////////
        blockView.setOnClickListener {

            if(flag == 0){
                val intent = Intent(context, AccountTeacherActivity::class.java)
                intent.putExtra("key0",id)
                startActivity(intent)
            }else{
                val intent = Intent(context, AccountStudentActivity::class.java)
                intent.putExtra("key1",id)
                startActivity(intent)
            }

        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        id_search_tape_users.addView(blockView)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


}