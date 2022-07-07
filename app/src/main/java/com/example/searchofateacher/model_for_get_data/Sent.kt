package com.example.searchofateacher.model_for_get_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
Класс для получения данных об отправленных заявках(нужно для студента)
 */

@Parcelize
class Sent (

    var id_teacher:String = "",
    var number_document:Int = 0,
    var number_predmet:Int = 0

        ):Parcelable