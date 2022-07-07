package com.example.searchofateacher.firestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Firestore {

    val mFirestore = FirebaseFirestore.getInstance()

    //ФУНКЦИЯ ПОЛУЧЕНИЯ ID ЮЗЕРА////////////////////////////////
    fun getCurrentUserId():String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if(currentUser!= null){
            currentUserId = currentUser.uid
        }
        return currentUserId
    }
    ////////////////////////////////////////////////////////////

}