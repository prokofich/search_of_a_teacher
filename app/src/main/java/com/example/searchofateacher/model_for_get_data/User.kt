package com.example.searchofateacher.model_for_get_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
Класс для получения данных о пользователе
 */
@Parcelize
class User (

    var id:String = "",
    var firstname:String = "",
    var fullname:String = "",
    var email:String = "",
    var status:String = "",

    var count_in_monday:Int = 0,
    var count_in_tuesday:Int = 0,
    var count_in_wednesday:Int = 0,
    var count_in_thursday:Int = 0,
    var count_in_friday:Int = 0,
    var count_in_saturday:Int = 0,
    var count_in_sunday:Int = 0,

    var text:String = "",
    var mobile:String = "",
    var image:String = "",
    var profileCompleted: Int = 0,
    var count_predmets:Int = 0,
    var number_in_tape:Int = 0

        ):Parcelable