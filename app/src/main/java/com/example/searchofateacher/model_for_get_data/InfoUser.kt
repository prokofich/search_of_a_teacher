package com.example.searchofateacher.model_for_get_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
Класс для получения главных данных о пользователе(нужно для фрагмента с поиском)
 */

@Parcelize
class InfoUser(

    var id:String = "",
    var lastname:String = "",
    var name :String = "",
    var status:String =""

):Parcelable